package com.habitquest.controller;

import com.habitquest.common.CommonResponse;
import com.habitquest.common.Provider;
import com.habitquest.service.HabitService;
import com.habitquest.service.dto.HabitRequestDTO;
import com.habitquest.service.dto.HabitUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/habit")
public class HabitController {

  private final HabitService habitService;

  @Operation(summary = "습관 목록 조회", description = "사용자의 습관 목록을 조회합니다.")
  @GetMapping("list")
  public CommonResponse<?> getHabitList(@RequestParam Long userId) {
    return CommonResponse.success(habitService.getHabitList(userId));
  }

  @Operation(summary = "습관 상세 조회", description = "습관을 상세 조회합니다.")
  @GetMapping("{habitId}")
  public CommonResponse<?> getHabitDetail(@PathVariable Long habitId) {
    return CommonResponse.success(habitService.getHabitDetail(habitId));
  }

  @Operation(summary = "습관 생성", description = "습관을 생성합니다.")
  @PostMapping
  public void createHabit(@RequestBody HabitRequestDTO req) {
    req.check();
    habitService.createHabit(req);
  }

  @Operation(summary = "습관 수정", description = "습관을 수정합니다.")
  @PutMapping("{habitId}")
  public void updateHabit(@PathVariable Long habitId, @RequestBody HabitUpdateRequestDTO req) {
    req.check();
    habitService.updateHabit(habitId, req);
  }

  @Operation(summary = "습관 삭제", description = "습관을 삭제합니다.")
  @DeleteMapping("{habitId}")
  public void deleteHabit(@PathVariable Long habitId) {
    habitService.deleteHabit(habitId);
  }
}
