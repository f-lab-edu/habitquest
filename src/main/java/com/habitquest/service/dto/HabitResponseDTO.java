package com.habitquest.service.dto;

import com.habitquest.common.Color;
import com.habitquest.common.LevelType;
import com.habitquest.common.ResetType;
import com.habitquest.common.TagType;
import com.habitquest.entity.Habit;
import java.util.List;


public record HabitResponseDTO(String title, String notes, boolean positive, boolean negative,
                               Integer positiveCount, Integer negativeCount, Color color, LevelType level, List<TagType> tag, ResetType resetCounter) {

  public HabitResponseDTO(Habit habit) {
    this(
        habit.getTitle(),
        habit.getNotes(),
        habit.isPositive(),
        habit.isNegative(),
        habit.getPositiveCount(),
        habit.getNegativeCount(),
        habit.getColor(),
        habit.getLevel(),
        habit.getTag(),
        habit.getResetCounter()
    );

  }

}
