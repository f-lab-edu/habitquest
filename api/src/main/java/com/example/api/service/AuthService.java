package com.example.api.service;

import com.example.common.common.ErrorType;
import com.example.common.entity.User;
import com.example.common.exception.HabitQuestException;
import com.example.common.repository.UserRepository;
import com.example.api.security.TokenProvider;
import com.example.api.service.dto.LoginRequestDTO;
import com.example.api.service.dto.TokenResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  public TokenResponseDTO login(LoginRequestDTO userInfo) {
    // userNameOrEmail 값이 userName 또는 email 필드 데이터에 있는지 확인
    User user = userRepository.findUserByUserNameOrEmail(userInfo.userNameOrEmail(), userInfo.userNameOrEmail()).orElseThrow(() ->new HabitQuestException(ErrorType.INVALID_LOGIN));
    // 비밀번호 검증
    if (!passwordEncoder.matches(userInfo.password(), user.getPassword())) {
      throw new HabitQuestException(ErrorType.INVALID_LOGIN);
    }

    // 토큰 생성
    String accessToken = tokenProvider.createAccessToken(user.getUserName(),user.getId());
    String refreshToken = tokenProvider.createRefreshToken(user.getUserName(),user.getId());

    return new TokenResponseDTO(accessToken, refreshToken);
  }
}
