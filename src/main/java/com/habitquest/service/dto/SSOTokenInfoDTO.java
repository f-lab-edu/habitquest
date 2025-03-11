package com.habitquest.service.dto;

public record SSOTokenInfoDTO(String accessToken, Integer expiresIn, String idToken)
{}
