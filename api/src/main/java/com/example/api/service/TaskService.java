package com.example.api.service;

import com.example.common.common.LevelType;
import com.example.common.entity.Task;
import com.example.common.repository.TaskRepository;
import com.example.api.service.dto.CursorRequestDTO;
import com.example.api.service.dto.CursorScrollResponse;
import com.example.api.service.dto.TaskResponseDTO;
import com.example.api.service.dto.TaskScrollListRequestDTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  private static final BigDecimal MIN_RATE = new BigDecimal("0.5");
  private static final BigDecimal MAX_RATE = new BigDecimal("3.5");
  private static final BigDecimal MULTI_RATE = new BigDecimal("2.0");

  public List<TaskResponseDTO> getTaskList(Long userId) {
    List<Task> taskList = taskRepository.findAllByUserId(userId);
    return taskList.stream().map(TaskResponseDTO::new).toList();
  }

  public CursorScrollResponse<TaskResponseDTO> getScrollTaskList(TaskScrollListRequestDTO requestDTO) {

    // 데이터가 마지막인지 확인하기 위해 요청 size보다 1 크게 설정
    int changeSize = requestDTO.size() + 1;

    List<Task> changeSizeTaskList = taskRepository.findTasksByCursor(requestDTO.userId(), requestDTO.type(),requestDTO.cursorCreatedAt(),
        requestDTO.cursorTaskId(), CursorRequestDTO.toPageable(changeSize));

    // 조회 리스트 size와 요청 size를 비교, 요청 size보다 클 경우, 다음 데이터가 있다고 판단
    boolean hasNext = changeSizeTaskList.size() > requestDTO.size();

    // 요청 size대로 실제 반환 task list 담기
    List<Task> taskList = hasNext ? changeSizeTaskList.subList(0, requestDTO.size()) : changeSizeTaskList;

    // 마지막 커서의 위치를 같이 전달해주기 위해, tasklist의 마지막 데이터(createdAt, taskId) 추출
    // API에서 마지막 커서 위치를 전달해주게 되면, 프론트에서는 굳이 데이터 확인 없이 해당 커서 정보로 요청하면됨
    Task lastTask = taskList.get(taskList.size() - 1);

    CursorRequestDTO nextCursor = null;
    if (hasNext) {
      nextCursor = new CursorRequestDTO(lastTask.getCreatedAt(), lastTask.getId());
    }

    List<TaskResponseDTO> responseList = taskList.stream().map(TaskResponseDTO::new).toList();
    return new CursorScrollResponse<>(responseList, hasNext, nextCursor);
  }

//  public void positiveCountUp(Long taskId) {
//    Task task = taskRepository.findTaskById(taskId);
//
//    //
//
//  }



  // 긍정적 카운트 보상 계산기
  public void positiveCountUp(Long taskId) {
    Task task = taskRepository.findTaskById(taskId);
    LevelType taskLevel = task.getLevel();

    // task 난이도에 따른 기본 골드 & 경험치 보상 가져오기
    BigDecimal rewardGold = taskLevel.getBaseRewardGold();
    int rewardExp = taskLevel.getBaseRewardExp();

    // 레벨 별 보상 증가를 위한 설정 (gold는 2배수, exp는 10배수)
    int levelGoldBonusStep = task.getUser().getLevel() / 2;
    int levelExpBonusStep = task.getUser().getLevel() / 10;

    // 기본 보상 골드  + (난이도 별 증가량 * 2배수 레벨 단계)
    rewardGold = rewardGold.add(taskLevel.getGoldIncrement().multiply(BigDecimal.valueOf(levelGoldBonusStep)));
    // 기본 보상 경험치  + (난이도 별 증가량 * 10배수 레벨)
    rewardExp += taskLevel.getExpIncrement() * levelExpBonusStep;

    // 긍정적+부정적 총 카운트 개수에 따른 획득량 감소 처리
    int count = task.getPositiveCount() - task.getNegativeCount();
    BigDecimal decayRatio;

    if (count >= 0) {
      decayRatio = BigDecimal.ONE.subtract(taskLevel.getRewardRate().multiply(BigDecimal.valueOf(count)));
    } else {
      // count가 음수일 경우, 보상률이 강해지도록 비율 조정 추가
      decayRatio = BigDecimal.ONE.add(taskLevel.getRewardRate().multiply(BigDecimal.valueOf(count)).multiply(MULTI_RATE));
    }

    BigDecimal decimalRewardExp = BigDecimal.valueOf(rewardExp);

    // 골드, 경험치에 카운트 적용 보상 비율 적용, 최소 최대 값 설정
    rewardGold = rewardGold.multiply(decayRatio)
        .min(rewardGold.multiply(MIN_RATE))
        .max(rewardGold.multiply(MAX_RATE));

    rewardExp = decimalRewardExp.multiply(decayRatio)
        .min(decimalRewardExp.multiply(MIN_RATE))
        .max(decimalRewardExp.multiply(MAX_RATE))
        .setScale(0, RoundingMode.FLOOR)
        .intValue();

    // positive count 증가 업데이트


  }


  // 부정적 카운트 패널티 메서드
  public void negativeCountUp() {

  }



}
