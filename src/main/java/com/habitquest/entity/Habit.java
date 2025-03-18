package com.habitquest.entity;

import com.habitquest.common.Color;
import com.habitquest.common.LevelType;
import com.habitquest.common.ResetType;
import com.habitquest.common.TagType;
import com.habitquest.service.dto.HabitRequestDTO;
import com.habitquest.service.dto.HabitUpdateRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.vladmihalcea.hibernate.type.json.JsonType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Habit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private String title;
  private String notes;
  private boolean positive;
  private boolean negative;
  @Enumerated(EnumType.STRING)
  private LevelType level;
  @Type(JsonType.class)
  @Column(columnDefinition = "json")
  private List<TagType> tag;
  @Enumerated(EnumType.STRING)
  private ResetType resetCounter;
  @Column(columnDefinition = "int default 0")
  private Integer positiveCount;
  @Column(columnDefinition = "int default 0")
  private Integer negativeCount;
  @Enumerated(EnumType.STRING)
  private Color color;
  private LocalDateTime lastPerformedAt;
  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
  private LocalDateTime createdAt;

  public Habit(HabitRequestDTO req) {
    this.userId = req.userId();
    this.title = req.title();
    this.notes = req.notes();
    this.positive = req.positive();
    this.negative = req.negative();
    this.level = req.level();
    this.tag = req.tag();
    this.resetCounter = req.resetCounter();
    this.positiveCount = 0;
    this.negativeCount = 0;
    this.color = Color.YELLOW;
  }

  public void update(HabitUpdateRequestDTO req) {
    this.title = req.title();
    this.notes = req.notes();
    this.positive = req.positive();
    this.negative = req.negative();
    this.level = req.level();
    this.tag = req.tag();
    this.resetCounter = req.resetCounter();
  }
}
