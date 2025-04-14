package com.example.api.service.dto;

import com.example.common.common.DayOfWeek;
import com.example.common.entity.TaskRepeatSetting;

public record TaskRepeatSettingDTO(
    Long id,
    boolean su,
    boolean mo,
    boolean tu,
    boolean we,
    boolean th,
    boolean fr,
    boolean sa,
    boolean isMonthly,
    int dayOfMonth,
    int weekNumber,
    DayOfWeek dayOfWeek
) {

  public TaskRepeatSettingDTO(TaskRepeatSetting setting) {
    this(setting.getId(), setting.isSu(), setting.isMo(), setting.isTu(), setting.isWe(),
        setting.isTh(), setting.isFr(), setting.isSa(), setting.isMonthly(), setting.getDayOfMonth(),
        setting.getWeekNumber(), setting.getDayOfWeek());
  }
}
