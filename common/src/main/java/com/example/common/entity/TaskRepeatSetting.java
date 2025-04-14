package com.example.common.entity;

import com.example.common.common.DayOfWeek;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class TaskRepeatSetting {

  @Id
  @GeneratedValue
  private Long id;
  private boolean su;
  private boolean mo;
  private boolean tu;
  private boolean we;
  private boolean th;
  private boolean fr;
  private boolean sa;

  @Column(name = "is_monthly")
  private boolean isMonthly;

  @Column(name = "day_of_month")
  private int dayOfMonth;

  @Column(name = "week_number")
  private int weekNumber;

  @Column(name = "day_of_week")
  @Enumerated(EnumType.STRING)
  private DayOfWeek dayOfWeek;


}
