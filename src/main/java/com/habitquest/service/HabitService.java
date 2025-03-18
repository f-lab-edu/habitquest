package com.habitquest.service;

import com.habitquest.common.ErrorType;
import com.habitquest.entity.Habit;
import com.habitquest.exception.HabitQuestException;
import com.habitquest.repository.HabitRepository;
import com.habitquest.service.dto.HabitListResponseDTO;
import com.habitquest.service.dto.HabitRequestDTO;
import com.habitquest.service.dto.HabitResponseDTO;
import com.habitquest.service.dto.HabitUpdateRequestDTO;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {
  private final HabitRepository habitRepository;

  public List<HabitListResponseDTO> getHabitList(Long userId) {
    List<Habit> habitList = habitRepository.findAllByUserId(userId);
    return habitList.stream().map(HabitListResponseDTO::new).toList();
  }

  public HabitResponseDTO getHabitDetail(Long habitId) {
    Habit habit = habitRepository.findHabitById(habitId).orElseThrow(() -> new HabitQuestException(
        ErrorType.NOT_FOUND));
    return new HabitResponseDTO(habit);
  }

  public void createHabit(HabitRequestDTO req) {
    Habit habit = new Habit(req);
    habitRepository.save(habit);
  }

  @Transactional
  public void updateHabit(Long habitId, HabitUpdateRequestDTO req) {
    Habit habit = habitRepository.findHabitById(habitId).orElseThrow(() -> new HabitQuestException(ErrorType.NOT_FOUND));
    habit.update(req);
  }

  public void deleteHabit(Long habitId) {
    if (!habitRepository.existsById(habitId)) {
      throw new HabitQuestException(ErrorType.NOT_FOUND);
    }
    habitRepository.deleteById(habitId);
  }
}
