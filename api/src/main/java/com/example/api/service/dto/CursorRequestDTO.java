package com.example.api.service.dto;
import java.time.LocalDateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record CursorRequestDTO(LocalDateTime createdAt, Long id) {

  public static Pageable toPageable(int size) {
    return PageRequest.of(0, size, Sort.by("createdAt").descending().and(Sort.by("id").descending()));
  }
}
