package com.example.api.controller;

import com.example.api.security.AuthUserDetails;
import com.example.api.service.UserService;
import com.example.api.service.dto.UserRequestDTO;
import com.example.api.service.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;

  @Operation(summary = "일반 회원가입", description = "사용자 이름, 이메일, 비밀번호를 입력하여 회원가입 합니다.")
  @PostMapping("signup")
  public void signUp(@RequestBody UserRequestDTO userInfo) {
    userInfo.check();
    userService.signUp(userInfo);
  }

  @Operation(summary = "사용자 이름 중복 체크", description = "사용자 이름이 사용 가능한지 확인합니다.")
  @GetMapping("check-username")
  public void checkUserName(@RequestParam String userName) {
    userService.checkDuplicateUserName(userName);
  }

  @Operation(summary = "사용자 조회", description = "사용자 정보를 조회합니다.")
  @GetMapping
  public UserResponseDTO getUser(@AuthenticationPrincipal AuthUserDetails userInfo) {
    return userService.getUser(userInfo.userId());
  }
}
