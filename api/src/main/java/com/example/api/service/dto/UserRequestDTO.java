package com.example.api.service.dto;
import com.example.common.common.ErrorType;
import com.example.common.exception.HabitQuestException;
import com.example.api.service.ValidationCheck;
import com.example.common.util.ValidationUtil;

public record UserRequestDTO(String userName,
                             String email,
                             String password,
                             String confirmPassword )implements ValidationCheck {

  @Override
  public void check() {
    // null 체크
    if (userName == null || userName.isBlank()) {
      throw new HabitQuestException(ErrorType.REQUIRED_FIELD_EMPTY_NAME);
    }
    if (email == null || email.isBlank()) {
      throw new HabitQuestException(ErrorType.REQUIRED_FIELD_EMPTY_EMAIL);
    }
    if (password == null || password.isBlank()) {
      throw new HabitQuestException(ErrorType.REQUIRED_FIELD_EMPTY_PASSWORD);
    }
    if (confirmPassword == null || confirmPassword.isBlank()) {
      throw new HabitQuestException(ErrorType.REQUIRED_FIELD_EMPTY_CONFIRM_PASSWORD);
    }
    // util 체크 메소드 호출
    ValidationUtil.passwordEqualCheck(password, confirmPassword);
    ValidationUtil.passwordValidationCheck(password);
  }
}
