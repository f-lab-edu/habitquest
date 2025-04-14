package com.example.api.service.dto;

import com.example.common.common.ErrorType;
import com.example.common.exception.HabitQuestException;
import com.example.api.service.ValidationCheck;

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
