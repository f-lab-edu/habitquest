package com.example.common.exception;

import com.example.common.common.ErrorType;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class HabitQuestException extends RuntimeException {
  private ErrorType errorType;
  private Map<String, Object> parameters;

  public HabitQuestException(ErrorType errorType) {
    this(errorType,new HashMap<>());

  }
  public HabitQuestException(ErrorType errorType, Map<String, Object> parameters) {
    this.errorType = errorType;
    this.parameters = parameters;
  }
}
