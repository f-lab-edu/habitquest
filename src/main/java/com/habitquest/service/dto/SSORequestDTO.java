package com.habitquest.service.dto;

import com.habitquest.common.ErrorType;
import com.habitquest.common.Provider;
import com.habitquest.exception.HabitQuestException;
import com.habitquest.service.ValidationCheck;

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
