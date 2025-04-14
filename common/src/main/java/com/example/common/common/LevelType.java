package com.example.common.common;

import java.math.BigDecimal;

public enum LevelType {
  TRIVIAL(new BigDecimal("0.1"),BigDecimal.ZERO,1, 0, BigDecimal.ZERO, 1),
  EASY(new BigDecimal("1.0"),new BigDecimal("0.02"),6, 0, new BigDecimal("0.018"),2),
  MEDIUM(new BigDecimal("1.5"),new BigDecimal("0.03"),9, 1, new BigDecimal("0.025"),3),
  HARD(new BigDecimal("2.0"),new BigDecimal("0.04"),12, 1, new BigDecimal("0.035"),4);

  private final BigDecimal baseRewardGold;
  private final BigDecimal goldIncrement;
  private final int baseRewardExp;
  private final int expIncrement;
  private final BigDecimal rewardRate;
  private final int reduceHealth;

  LevelType(BigDecimal baseRewardGold, BigDecimal goldIncrement, int baseRewardExp, int expIncrement, BigDecimal rewardRate, int reduceHealth) {
    this.baseRewardGold = baseRewardGold;
    this.goldIncrement = goldIncrement;
    this.baseRewardExp = baseRewardExp;
    this.expIncrement = expIncrement;
    this.rewardRate = rewardRate;
    this.reduceHealth = reduceHealth;
  }

  public BigDecimal getBaseRewardGold() {
    return baseRewardGold;
  }

  public BigDecimal getGoldIncrement() {
    return goldIncrement;
  }

  public int getBaseRewardExp() {
    return baseRewardExp;
  }

  public int getExpIncrement() {
    return expIncrement;
  }

  public BigDecimal getRewardRate() { return rewardRate; }

  public int getReduceHealth() { return reduceHealth; }
}
