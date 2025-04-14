package com.example.api.service.dto;

import com.example.common.common.TaskType;
import java.time.LocalDateTime;

public record TaskScrollListRequestDTO(Long userId, TaskType type, int size,LocalDateTime cursorCreatedAt, Long cursorTaskId) {
}
