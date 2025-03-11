package com.habitquest.service.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

// refresh token을 반환하지 않을 경우 직렬화되지 않도록 JsonInclude 설정
@JsonInclude(Include.NON_NULL)
public record TokenResponseDTO(String accessToken, String refreshToken)
{}
