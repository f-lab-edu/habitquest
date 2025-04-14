package com.habitquest.service;

import com.habitquest.common.ErrorType;
import com.habitquest.config.ObjectMapperConfig;
import com.habitquest.exception.HabitQuestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenService {
  @Value("${jwt.secret-key}")
  private String secretKey;
  @Value("${jwt.expire-milliseconds}")
  private long expireMilliseconds;
  @Value("${jwt.refresh-expire-milliseconds}")
  private long refreshExpireMilliseconds;
  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapperConfig objectMapper;
  public static String accessTokenKey = "jwt:access:";
  public static String refreshTokenKey = "jwt:refresh:";

  // secretKey를 HMAC SHA 알고리즘에 맞는 Key 객체로 변환하는 메서드
  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public String createAccessToken(String userName) {
    // JWT 생성
    String accessToken = Jwts.builder()
        .subject(userName)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1800000))
        .signWith(getSigningKey())
        .compact();

    // redis 저장
    log.info("user access token redis save start...{}", userName);
    redisTemplate.opsForValue().set(accessTokenKey + userName, accessToken, expireMilliseconds, TimeUnit.MILLISECONDS);

    return accessToken;
  }

  public String createRefreshToken(String userName) {
    // JWT 생성
    String refreshToken = Jwts.builder()
        .subject(userName)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + refreshExpireMilliseconds))
        .signWith(getSigningKey())
        .compact();

    // redis 저장
    log.info("user refresh token redis save start...{}", userName);
    redisTemplate.opsForValue().set(refreshTokenKey + userName, refreshToken, refreshExpireMilliseconds, TimeUnit.MILLISECONDS);
    return refreshToken;
  }

  public Map<String, Object> getTokenPayloadClaim(String token) {
    String[] parts = token.split("\\.");
    // jwt의 header, payload에는 url-safe base64 인코딩이 되어 있기에, urlDecoder를 사용해야함
    // byte로 응답값이 오며, string으로 변환되어 저장
    String payloadPart = new String(Base64.getUrlDecoder().decode(parts[1]));
    try {
      // json -> Map 객체로 변환
      Map<String, Object> payload = objectMapper.objectMapper().readValue(payloadPart, Map.class);
      return payload;
    } catch (Exception e) {
        throw new HabitQuestException(ErrorType.USERNAME_CONFLICT);
    }
  }

  public Claims validateToken(String token) {
    try {
      return Jwts.parser()
          .verifyWith(getSigningKey()) // secretkey로 서명 검증하여 위조된 토큰인지 확인
          .build()
          .parseSignedClaims(token)// jwt를 해석해서 내부 정보를 가져옴. 만료 시간도 함께 체크함
          .getPayload(); // 검증 성공시 jwt payload를 claims 형태로 리턴
    } catch (Exception e) {
      throw new HabitQuestException(ErrorType.USER_UNAUTHORIZED);
    }
  }

  public void deleteRedisTokensByUserName(String userName) {
    log.info("redis user token delete start ....user : {}", userName);
    redisTemplate.delete(accessTokenKey + userName);
    redisTemplate.delete(refreshTokenKey + userName);
  }
}
