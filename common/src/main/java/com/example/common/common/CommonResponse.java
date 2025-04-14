package com.example.common.common;

import org.springframework.http.HttpStatus;

public record CommonResponse<T>(
    String code,
    int httpStatus,
    String message,
    T data
) {

  public static<T> CommonResponse<T> success(T data) {
    return new CommonResponse<>("000001",HttpStatus.OK.value(),"요청이 성공적으로 처리되었습니다.", data);
  }
}
