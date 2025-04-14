package com.example.api.service.dto;

import com.example.common.entity.User;
import java.math.BigDecimal;

public record UserResponseDTO(
    Long id,
    String userName,
    String userDisplayName,
    String email,
    BigDecimal gold,
    int exp,
    int level,
    int requiredLevelUpExp
) {
  public UserResponseDTO(User user, int requiredLevelUpExp) {
    this(user.getId(), user.getUserName(), user.getUserDisplayName(),user.getEmail(),user.getGold(),user.getExp(),user.getLevel(), requiredLevelUpExp);
  }
}
