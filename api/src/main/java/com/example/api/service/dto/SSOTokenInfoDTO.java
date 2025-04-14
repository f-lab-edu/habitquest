package com.example.api.service.dto;

public record SSOTokenInfoDTO(String accessToken, Integer expiresIn, String idToken)
{}
