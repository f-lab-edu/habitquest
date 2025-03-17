package com.habitquest.filter;

import com.habitquest.common.ErrorType;
import com.habitquest.config.SecurityConfig;
import com.habitquest.exception.HabitQuestException;
import com.habitquest.service.TokenService;
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
  private final TokenService tokenService;
  private final RedisTemplate<String, String> redisTemplate;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    System.out.println("Exclude Path: " + Arrays.toString(SecurityConfig.excludePath));
    String path = request.getRequestURI();
    System.out.println("path : " + path);
    return Arrays.stream(SecurityConfig.excludePath).anyMatch(exclude -> pathMatcher.match(exclude, path));
  }
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

    String accessToken = resolveToken(request);
    // jwt 유효성 검사
    Claims claims = tokenService.validateToken(accessToken);

    try {
      // user 추출
      String userName = claims.getSubject();

      // redis에서 사용자 access token 추출
      String redisUserToken = redisTemplate.opsForValue().get(TokenService.accessTokenKey + userName);
      log.info("redis token ===> {}", redisUserToken);

      if (redisUserToken == null || !redisUserToken.equals(accessToken)) {
        throw new HabitQuestException(ErrorType.USER_UNAUTHORIZED);
      }
      // spring security에 등록할 인증 정보 설정
      Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, Collections.emptyList());

      // 인증 정보 등록
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(request, response);

    } catch (Exception e) {
      if (e instanceof HabitQuestException) {
        throw (HabitQuestException) e;
      } else {
        throw new HabitQuestException(ErrorType.UNKNOWN_ERROR);
      }
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
