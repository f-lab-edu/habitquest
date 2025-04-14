package com.habitquest.exception;

import com.google.common.base.Joiner;
import com.habitquest.common.CommonResponse;
import com.habitquest.common.ErrorType;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HabitQuestExceptionHandler {

  @ExceptionHandler(value = HabitQuestException.class)
  public ResponseEntity<CommonResponse<?>> exceptionHandler(HabitQuestException e) {
    ErrorType errorType = e.getErrorType();
    Map<String,Object> parameters = e.getParameters();
    String param = Joiner.on(",").withKeyValueSeparator("=").join(parameters);
    log.error(errorType.getMessage() + param);

    return ResponseEntity.status(errorType.getHttpStatus()).body(new CommonResponse<>(errorType.getCode(), errorType.getHttpStatus(), errorType.getMessage(), null));
  }
}