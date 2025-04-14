package com.example.api.service.dto;

import java.util.List;

public record CursorScrollResponse<T>(
    List<T> contents, // 응답 데이터
    boolean hasNext, // 다음 데이터 유무
    CursorRequestDTO nextCursorInfo // 마지막 커서 데이터 정보
) {
}
