package com.example.api.service.dto;

import com.example.common.common.ErrorType;
import com.example.common.common.Provider;
import com.example.common.exception.HabitQuestException;
import com.example.api.service.ValidationCheck;

public record SSORequestDTO(Provider provider, String code) implements ValidationCheck {
  @Override
  public void check() {
    if (provider == null) {
      throw new HabitQuestException(ErrorType.REQUIRED_FIELD_EMPTY_PROVIDER);
    }
    if (code == null || code.isBlank()) {
      throw new HabitQuestException(ErrorType.REQUIRED_FIELD_EMPTY_CODE);
    }
  }

}
