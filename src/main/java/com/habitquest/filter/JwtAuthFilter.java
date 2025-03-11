package com.habitquest.filter;

import com.habitquest.common.ErrorType;
import com.habitquest.exception.HabitQuestException;
import com.habitquest.util.TokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final TokenUtil tokenUtil;
  private final RedisTemplate<String, String> redisTemplate;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    String[] excloudePath = {"api/v1/auth/login",
        "api/v1/auth/login/**",
        "api/v1/user/check-username",
        "/swagger-ui/**",
        "/v3/api-docs/**"};
    String path = request.getRequestURI();
    return Arrays.stream(excloudePath).anyMatch(exclude -> pathMatcher.match(exclude, path));

  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

    String accessToken = resolveToken(request);
    log.info("accesstoken ==> {}", accessToken);

    try {
      // jwt 유효성 검사
      Claims claims = tokenUtil.validateToken(accessToken);

      // user 추출
      String userName = claims.getSubject();

      // redis에서 사용자 access token 추출
      String redisUserToken = redisTemplate.opsForValue().get("jwt:access:" + userName);
      log.info("redis token ===> {}", redisUserToken);

      if (redisUserToken == null || !redisUserToken.equals(accessToken)) {
        throw new HabitQuestException(ErrorType.USER_UNAUTHORIZED);
      }

      // spring security에 등록할 인증 정보 설정
      Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, Collections.emptyList());

      // 인증 정보 등록
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(request, response);

    } catch (HabitQuestException e) {
      throw new HabitQuestException(ErrorType.USER_UNAUTHORIZED);
    }

  }

  private String resolveToken(HttpServletRequest request) {
    // bearer 문자열 포함된 토큰 추출
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
      throw new HabitQuestException(ErrorType.USER_UNAUTHORIZED);
    }
    // bearer 제거 후 access token 반환
    return bearerToken.substring(7);
  }

}
