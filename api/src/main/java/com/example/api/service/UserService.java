package com.example.api.service;

import com.example.common.common.ErrorType;
import com.example.common.entity.User;
import com.example.common.exception.HabitQuestException;
import com.example.common.repository.UserRepository;
import com.example.api.service.dto.UserRequestDTO;
import com.example.api.service.dto.UserResponseDTO;
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

  public void checkDuplicateUserName(String userName) {
    if (userRepository.existsByUserName(userName)) {
      throw new HabitQuestException(ErrorType.USERNAME_CONFLICT, Map.of("username",userName));
    }
  }

  public UserResponseDTO getUser(Long userId) {
    User user = userRepository.findUserById(userId).orElseThrow(() -> new HabitQuestException(
        ErrorType.NOT_FOUND));

    int requiredLevelUpExp = levelExpCalculator(user.getLevel());
    return new UserResponseDTO(user, requiredLevelUpExp);
  }



  // 레벨 별 필요한 경험치 계산 메소드
  public int levelExpCalculator(int level) {
    if (level < 5) {
      return 25 * level;
    } else if (level == 5) {
      return 150;
    }
    double result = (Math.pow(level, 2) * 0.25 + 10 * level + 139.75) / 10;
    return (int) Math.round(result) * 10;
  }

}
