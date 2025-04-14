package com.habitquest.service;

import com.habitquest.common.ErrorType;
import com.habitquest.service.dto.UserRequestDTO;
import com.habitquest.entity.User;
import com.habitquest.exception.HabitQuestException;
import com.habitquest.repository.UserRepository;
import com.habitquest.common.Provider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public void signUp(UserRequestDTO userInfo) {

    checkDuplicateUserName(userInfo.userName());

    if (userRepository.existsByEmail(userInfo.email())) {
      throw new HabitQuestException(ErrorType.EMAIL_CONFLICT);
    }

    User user = User.builder()
        .userName(userInfo.userName())
        .password(passwordEncoder.encode(userInfo.password()))
        .userDisplayName(userInfo.userName())
        .email(userInfo.email())
        .provider(null)
        .build();

    userRepository.save(user);
  }

  // 사용자 이름 중복 체크
  public void checkDuplicateUserName(String userName) {
    if (userRepository.existsByUserName(userName)) {
      throw new HabitQuestException(ErrorType.USERNAME_CONFLICT, Map.of("username",userName));
    }
  }
}
