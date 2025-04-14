package com.example.common.entity;

import com.example.common.common.TagType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class TaskTagMapping {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "tag_name")
  @Enumerated(EnumType.STRING)
  private TagType tagName;
}
