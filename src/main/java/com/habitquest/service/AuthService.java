package com.habitquest.service;

import com.habitquest.common.ErrorType;
import com.habitquest.entity.User;
import com.habitquest.exception.HabitQuestException;
import com.habitquest.repository.UserRepository;
import com.habitquest.service.dto.LoginRequestDTO;
import com.habitquest.service.dto.TokenResponseDTO;
import com.habitquest.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenUtil tokenUtil;

  public TokenResponseDTO login(LoginRequestDTO userInfo) {
    // userNameOrEmail 값이 userName 또는 email 필드 데이터에 있는지 확인
    User user = userRepository.findUserByUserNameOrEmail(userInfo.userNameOrEmail(), userInfo.userNameOrEmail()).orElseThrow(() ->new HabitQuestException(ErrorType.INVALID_LOGIN));
    // 비밀번호 검증
    if (!passwordEncoder.matches(userInfo.password(), user.getPassword())) {
      throw new HabitQuestException(ErrorType.INVALID_LOGIN);
    }

    // 토큰 생성
    String accessToken = tokenUtil.createAccessToken(user.getUserName());
    String refreshToken = tokenUtil.createRefreshToken(user.getUserName());

    return new TokenResponseDTO(accessToken, refreshToken);
  }
}
