package com.example.api.service.dto;

import com.example.common.common.Color;
import com.example.common.common.LevelType;
import com.example.common.common.RepeatType;
import com.example.common.common.ResetType;
import com.example.common.common.TaskType;
import com.example.common.entity.Task;
import com.example.common.vo.CheckList;
import java.time.LocalDateTime;
import java.util.List;

public record TaskResponseDTO(
    Long id,
    TaskType type,
    String title,
    String notes,
    boolean positive,
    boolean negative,
    LevelType level,
    List<TagResponseDTO> tagList,
    ResetType resetCounter,
    Integer positiveCount,
    Integer negativeCount,
    Color color,
    List<CheckList> checkList,
    LocalDateTime startDate,
    RepeatType repeats,
    TaskRepeatSettingDTO repeatSetting,
    LocalDateTime dueDate
) {

  public TaskResponseDTO(Task task) {
    this(
        task.getId(),
        task.getType(),
        task.getTitle(),
        task.getNotes(),
        task.isPositive(),
        task.isNegative(),
        task.getLevel(),
        task.getTagList().stream().map(tl -> new TagResponseDTO(tl.getId(), tl.getTagName())).toList(),
        task.getResetCounter(),
        task.getPositiveCount(),
        task.getNegativeCount(),
        task.getColor(),
        task.getCheckList(),
        task.getStartDate(),
        task.getRepeats(),
        task.getRepeatSetting() != null ? new TaskRepeatSettingDTO(task.getRepeatSetting()) : null,
        task.getDueDate()
    );
  }
}
