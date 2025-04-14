package com.example.api.controller;

import com.example.common.common.CommonResponse;
import com.example.common.common.TaskType;
import com.example.api.security.AuthUserDetails;
import com.example.api.service.TaskService;
import com.example.api.service.dto.TaskScrollListRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {

  private final TaskService taskService;

//  @Operation(summary = "task 목록 조회", description = "task 목록을 조회합니다.")
//  @GetMapping("list")
//  public CommonResponse<?> getTaskList(@AuthenticationPrincipal AuthUserDetails userInfo) {
//    return CommonResponse.success(taskService.getTaskList(userInfo.userId()));
//  }

  @Operation(summary = "task 목록 스크롤 조회", description = "task 목록을 스크롤 형식으로 조회합니다.")
  @GetMapping("list")
  public CommonResponse<?> getScrollTaskList(
      @AuthenticationPrincipal AuthUserDetails userInfo,
      @RequestParam(required = false)TaskType type,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursorCreatedAt,
      @RequestParam(required = false) Long cursorTaskId) {

    TaskScrollListRequestDTO requestDTO = new TaskScrollListRequestDTO(userInfo.userId(), type, size, cursorCreatedAt, cursorTaskId);
    return CommonResponse.success(taskService.getScrollTaskList(requestDTO));
  }
}
