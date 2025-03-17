package com.habitquest.controller;

import com.habitquest.common.CommonResponse;
import com.habitquest.service.AuthService;
import com.habitquest.service.TakeSSOProvider;
import com.habitquest.service.dto.LoginRequestDTO;
import com.habitquest.service.dto.SSORequestDTO;
import com.habitquest.common.Provider;
import com.habitquest.service.dto.TokenResponseDTO;
import com.habitquest.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;
  private final TakeSSOProvider takeSSOProvider;
  private final TokenService tokenService;


  @Operation(summary = "일반 로그인", description = "사용자 이름 또는 이메일과 비밀번호로 로그인합니다.")
  @PostMapping("login")
  public CommonResponse<?> login(@RequestBody LoginRequestDTO userInfo) {
    userInfo.check();
    return CommonResponse.success(authService.login(userInfo));
  }


  @Operation(summary = "SSO 로그인 url 조회", description = "provider의 인증을 위한 url을 조회합니다.")
  @GetMapping("login/sso/url/{provider}")
  public CommonResponse<?> getAuthUrl(
      @PathVariable Provider provider) {
    return CommonResponse.success(takeSSOProvider.getAuthUrl(provider));
  }

  /*
    sso-login API에서는 provider에서 인증 후 인가 코드를 받아왔다는 전제 하애 잔행됨
   */
  @Operation(summary = "SSO 로그인", description = "SSO 방식으로 로그인 합니다.")
  @PostMapping("login/sso")
  public CommonResponse<?> ssoLogin(@RequestBody SSORequestDTO reqInfo) {
    reqInfo.check();
    return CommonResponse.success(takeSSOProvider.ssoLogin(reqInfo.provider(),reqInfo.code()));
  }

  @Operation(summary = "로그아웃", description = "로그아웃 합니다.")
  @GetMapping("logout")
  public void logout(Authentication authentication) {
    // JwtAuthFilter에서 토큰 검증 후 SecurityContextHolder에 사용자 정보를 저장하고 있음
    tokenService.deleteRedisTokensByUserName(authentication.getName());
  }

  @Operation(summary = "access token 재발급", description = "access token을 재발급 합니다.")
  @PostMapping("token")
  public CommonResponse<?> reissuedToken(Authentication authentication) {
    return CommonResponse.success(new TokenResponseDTO(tokenService.createAccessToken(authentication.getName()),null));
  }
}
