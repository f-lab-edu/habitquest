package com.example.common.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
  BAD_REQUEST("100001", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
  REQUIRED_FIELD_EMPTY_NAME("100002", HttpStatus.BAD_REQUEST, "이름이 입력되지 않았습니다."),
  REQUIRED_FIELD_EMPTY_EMAIL("100003", HttpStatus.BAD_REQUEST, "이메일이 입력되지 않았습니다."),
  REQUIRED_FIELD_EMPTY_PASSWORD("100004", HttpStatus.BAD_REQUEST, "비밀번호가 입력되지 않았습니다."),
  REQUIRED_FIELD_EMPTY_CONFIRM_PASSWORD("100005", HttpStatus.BAD_REQUEST, "비밀번호 확인 값이 입력되지 않았습니다."),
  REQUIRED_FIELD_EMPTY_USERNAME_OR_EMAIL("100006", HttpStatus.BAD_REQUEST, "이름 또는 이메일이 입력되지 않았습니다 ."),
  INVALID_PASSWORD("100006", HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호입니다."),
  INVALID_LOGIN("100007", HttpStatus.BAD_REQUEST, "잘못된 로그인 정보입니다. 다시 입력하세요"),
  REQUIRED_FIELD_EMPTY_CODE("100008", HttpStatus.BAD_REQUEST, "코드 정보가 입력되지 않았습니다."),
  REQUIRED_FIELD_EMPTY_PROVIDER("100009", HttpStatus.BAD_REQUEST, "SSO 제공자 정보가 입력되지 않았습니다."),
  PASSWORD_NOT_EQUAL("110001", HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
  USERNAME_CONFLICT("110002", HttpStatus.CONFLICT, "이미 사용 중인 사용자 이름입니다."),
  EMAIL_CONFLICT("110003", HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
  USER_UNAUTHORIZED("110004", HttpStatus.UNAUTHORIZED, "인증에 실패하여 요청이 거부되었습니다."),
  REQUIRED_FIELD_EMPTY("110005", HttpStatus.BAD_REQUEST, "필수 입력값이 입력되지 않았습니다."),
  UNKNOWN_ERROR("120001", HttpStatus.INTERNAL_SERVER_ERROR, "예기치 않은 오류가 발생하였습니다."),
  NOT_FOUND("120002", HttpStatus.NOT_FOUND, "요청 결과를 찾을 수 없습니다.");


  ErrorType(String code, HttpStatus httpStatus, String message) {
    this.code = code;
    this.httpStatus = httpStatus;
    this.message = message;
  }

  private final String code;
  private final HttpStatus httpStatus;
  private final String message;
}
