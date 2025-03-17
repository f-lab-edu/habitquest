package com.habitquest.service;

import com.habitquest.common.Provider;
import com.habitquest.entity.User;
import com.habitquest.repository.UserRepository;
import com.habitquest.service.dto.SSOTokenInfoDTO;
import com.habitquest.service.dto.TokenResponseDTO;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleAuthService implements SSOAuth {
  // 변수 선언(임시)
  public static final String CLIENT_ID = "1029754061175-b3r070omn0nh356v8fig92o1eots6pug.apps.googleusercontent.com";
  public static final String CLIENT_SECRET = "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";
  public static final String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/google";
  public static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
  public static final String TOKEN_URL = "https://oauth2.googleapis.com/token";

  private final TokenService tokenService;
  private final UserRepository userRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public String getAuthUrl() {
    return AUTH_URL +
        "?client_id=" + CLIENT_ID +
        "&redirect_uri=" + REDIRECT_URI +
        "&response_type=code" +
        "&scope=openid%20email%20profile"; // id, email, profile 정보를 얻음
  }
  @Override
  public TokenResponseDTO ssoLogin(String code) {
    // 인증 코드로 google 토큰 조회
    SSOTokenInfoDTO ssoTokens = getSSOTokenInfo(code);

    //id token payload 추출
    Map<String, Object> payload = tokenService.getTokenPayloadClaim(ssoTokens.idToken());
    String email = (String) payload.get("email");
    String name = (String) payload.get("name");

    // email이 user DB에 존재하는지 확인
    if (!userRepository.existsByEmail(email)) {
      // 존재하지 않을경우, 사용자 생성
      log.info("user not found. sso user create start === {}", email);
      User user = User.builder()
          .userName(RandomStringUtils.randomAlphabetic(13))
          .password(null)
          .userDisplayName(name)
          .email(email)
          .provider(Provider.GOOGLE)
          .build();
      userRepository.save(user);
    }

    // google access token redis 저장
    String accessTokenKey = "sso:access:" + name;
    log.info("SSO user access token redis save start...{}", name);
    redisTemplate.opsForValue().set(accessTokenKey, ssoTokens.accessToken(), ssoTokens.expiresIn(), TimeUnit.SECONDS);

    // habitquest에서 사용할 access, refresh token 발급
    String accessToken = tokenService.createAccessToken(name);
    String refreshToken = tokenService.createRefreshToken(name);

    return new TokenResponseDTO(accessToken,refreshToken);
  }

  @Override
  public SSOTokenInfoDTO getSSOTokenInfo(String code) {
    // url 디코딩
    String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
    // header 세팅
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    // 요청할 param 세팅
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("code", decodedCode);
    params.add("client_id", CLIENT_ID);
    params.add("client_secret", CLIENT_SECRET);
    params.add("redirect_uri", REDIRECT_URI);
    params.add("grant_type", "authorization_code");
    params.add("scope", "openid email profile"); // ID Token 요청을 위해 추가

 // Spring의 RestTemplate은 MultiValueMap<String, String>을 사용하면 자동으로 application/x-www-form-urlencoded 형식으로 변환해줌
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Map> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request,
        Map.class);

    // 응답에서 Access Token, access token 만료시간,  ID Token 추출
    String ssoAccessToken = response.getBody().get("access_token").toString();
    Integer expiresIn = (Integer) response.getBody().get("expires_in");
    String idToken = response.getBody().get("id_token").toString();

    return new SSOTokenInfoDTO(ssoAccessToken, expiresIn, idToken);
  }
}
