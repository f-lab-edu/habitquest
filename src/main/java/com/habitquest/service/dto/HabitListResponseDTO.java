package com.habitquest.service.dto;

import com.habitquest.common.Color;
import com.habitquest.entity.Habit;

public record HabitListResponseDTO(String title, String notes, boolean positive, boolean negative, Integer positiveCount, Integer negativeCount, Color color) {

  public HabitListResponseDTO(Habit habit) {
    this(habit.getTitle(), habit.getNotes(), habit.isPositive(), habit.isNegative(), habit.getPositiveCount(),
        habit.getNegativeCount(), habit.getColor());
  }

}
