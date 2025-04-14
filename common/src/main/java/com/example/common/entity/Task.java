package com.example.common.entity;

import com.example.common.common.Color;
import com.example.common.common.LevelType;
import com.example.common.common.RepeatType;
import com.example.common.common.ResetType;
import com.example.common.common.TaskType;
import com.example.common.converter.CheckListConverter;
import com.example.common.vo.CheckList;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Table(name = "tasks")
public class Task {
  @Id @GeneratedValue
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  @Enumerated(EnumType.STRING)
  private TaskType type;
  private String title;
  private String notes;
  private boolean positive;
  private boolean negative;

  @Enumerated(EnumType.STRING)
  private LevelType level;

  @OneToMany
  @BatchSize(size = 100)
  @JoinColumn(name = "id")
  private List<TaskTagMapping> tagList = new ArrayList<>();

  @Column(name = "reset_counter")
  @Enumerated(EnumType.STRING)
  private ResetType resetCounter; // 리셋카운터 (매일, 매주, 매달)

  @Column(name = "positive_count", columnDefinition = "int default 0")
  private Integer positiveCount; // 긍정 카운트 개수

  @Column(name = "negative_count", columnDefinition = "int default 0")
  private Integer negativeCount; // 부정 카운트 개수

  @Column(name = "adjust_count", columnDefinition = "int default 0")
  private Integer adjustCount; // 사용자 정의 카운트 수

  @Enumerated(EnumType.STRING)
  private Color color; // 습관 색

  @Column(name = "last_performed_at")
  private LocalDateTime lastPerformedAt; // 마지막 습관 실행 여부

  @Lob
  @Column(columnDefinition = "LONGTEXT")
  @Convert(converter = CheckListConverter.class)
  private List<CheckList> checkList;

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Enumerated(EnumType.STRING)
  private RepeatType repeats;

  @Column(name = "repeats_every")
  private Integer repeatsEvery;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id")
  TaskRepeatSetting repeatSetting;

  @Column(name = "due_date")
  private LocalDateTime dueDate;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "is_success")
  private boolean isSuccess;

  public void isSuccessInit() {
    this.isSuccess = false;
  }

}
