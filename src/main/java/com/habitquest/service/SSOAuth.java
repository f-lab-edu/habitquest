package com.habitquest.service;

import com.habitquest.service.dto.SSOTokenInfoDTO;
import com.habitquest.service.dto.TokenResponseDTO;

public interface SSOAuth {
  public String getAuthUrl();
  public TokenResponseDTO ssoLogin(String code);
  public SSOTokenInfoDTO getSSOTokenInfo(String code);
}
