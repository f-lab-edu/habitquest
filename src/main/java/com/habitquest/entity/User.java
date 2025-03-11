package com.habitquest.entity;

import com.habitquest.common.Provider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NonNull
  @Column(nullable = false)
  private String userName;
  private String userDisplayName;
  @NonNull
  @Column(nullable = false)
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private Provider provider;

  @Builder
  public User(String userName, String userDisplayName, String email, String password, Provider provider) {
    this.userName = userName;
    this.userDisplayName = userDisplayName;
    this.email = email;
    this.password = password;
    this.provider = provider;
  }
}
