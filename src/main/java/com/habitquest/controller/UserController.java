package com.habitquest.controller;

import com.habitquest.service.dto.UserRequestDTO;
import com.habitquest.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

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
}
