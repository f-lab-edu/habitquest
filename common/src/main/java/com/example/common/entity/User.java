package com.example.common.entity;

import com.example.common.common.Provider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // jpa에서 프록시 객체 생성시 기본 생성자가 필요하지만, 외부에서 직접 호출 못하도록 제한하기 위함
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
  private BigDecimal gold;
  private int exp;
  private int level;
  private int health;

  @Builder
  public User(String userName, String userDisplayName, String email, String password, Provider provider) {
    this.userName = userName;
    this.userDisplayName = userDisplayName;
    this.email = email;
    this.password = password;
    this.provider = provider;
    this.gold = new BigDecimal("0.00");
    this.exp = 0;
    this.level = 1;
    this.health = 50;
  }

  public void reduceHealth(int amount) {
    this.health = Math.max(0, this.health - amount);
  }
}
