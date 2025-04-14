package com.example.api.service;

import com.example.api.service.dto.SSOTokenInfoDTO;
import com.example.api.service.dto.TokenResponseDTO;

public interface SSOAuth {
  public String getAuthUrl();
  public TokenResponseDTO ssoLogin(String code);
  public SSOTokenInfoDTO getSSOTokenInfo(String code);
}
