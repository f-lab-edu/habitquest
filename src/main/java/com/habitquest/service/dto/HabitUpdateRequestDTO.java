package com.habitquest.service.dto;

import com.habitquest.common.ErrorType;
import com.habitquest.common.LevelType;
import com.habitquest.common.ResetType;
import com.habitquest.common.TagType;
import com.habitquest.exception.HabitQuestException;
import com.habitquest.service.ValidationCheck;
import java.util.List;

public record HabitUpdateRequestDTO(String title, String notes, boolean positive, boolean negative, LevelType level, List<TagType> tag, ResetType resetCounter) implements
    ValidationCheck {
  @Override
  public void check() {
    if (title == null || title.isBlank()) {
      throw new HabitQuestException(ErrorType.REQUIRED_FIELD_EMPTY);
    }
  }
}
