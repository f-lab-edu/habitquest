package com.habitquest.util;

import com.habitquest.common.ErrorType;
import com.habitquest.exception.HabitQuestException;

public class ValidationUtil {
  // 비밀번호, 확인 비밀번호 일치 체크 메소드
  public static void passwordEqualCheck(String password, String confirmPassword) {
    if (!password.equals(confirmPassword)) {
      throw new HabitQuestException(ErrorType.PASSWORD_NOT_EQUAL);
    }
  }
  // 비밀번호 유효성 검사 체크 메소드
  public static void passwordValidationCheck(String password) {
    // 하나 이상의 영문자, 숫자 포함 8자리 이상, 공백은 포함하지 않음
    String pattern = "^(?=.*[a-zA-Z])(?=.*[0-9])\\S{8,}$";
    if (!password.matches(pattern)) {
      throw new HabitQuestException(ErrorType.INVALID_PASSWORD);
    }
  }
}
