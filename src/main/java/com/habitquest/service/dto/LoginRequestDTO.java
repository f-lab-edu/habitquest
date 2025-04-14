package com.habitquest.service.dto;

import com.habitquest.common.ErrorType;
import com.habitquest.exception.HabitQuestException;
import com.habitquest.service.ValidationCheck;

public record LoginRequestDTO(String userNameOrEmail, String password) implements ValidationCheck {
  @Override
  public void check() {
    if (userNameOrEmail == null || userNameOrEmail.isBlank()) {
      throw new HabitQuestException(ErrorType.REQUIRED_FIELD_EMPTY_USERNAME_OR_EMAIL);
    }
    if (password == null || password.isBlank()) {
      throw new HabitQuestException(ErrorType.REQUIRED_FIELD_EMPTY_PASSWORD);
    }
  }
}
