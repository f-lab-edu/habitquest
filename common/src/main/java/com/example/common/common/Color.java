package com.example.common.common;

import java.util.Random;

public enum Color {
  // 습관 및 일일
  DARKRED(1.5, 2.0),
  RED(1.4, 1.5),
  ORANGE(1.3, 1.4),
  YELLOW(1.0,1.0),
  LIGHT_GREEN(0.7,0.9),
  GREEN(0.5,0.7),
  BLUE(0.3,0.5);

  private final double min;
  private final double max;
  private static final Random random = new Random();

  Color(double min, double max) {
    this.min = min;
    this.max = max;
  }

  // 색깔 별로 골드 및 경험치 추가 가중치 적용값 리턴 메소드
  public double getRandom() {
    return min + (max - min) * random.nextDouble();

  }


}
