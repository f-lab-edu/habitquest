package com.example.common.converter;

import com.example.common.common.ErrorType;
import com.example.common.exception.HabitQuestException;
import com.example.common.vo.CheckList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class CheckListConverter implements AttributeConverter<List<CheckList>, String> {

  private final ObjectMapper objectMapper;

  @Override
  public String convertToDatabaseColumn(List<CheckList> attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new HabitQuestException(ErrorType.UNKNOWN_ERROR);
    }
  }

  @Override
  public List<CheckList> convertToEntityAttribute(String dbData) {
    try {
      if (dbData != null) {
        return objectMapper.readValue(dbData, new TypeReference<>() {});
      } else {
        return null;
      }
    } catch (JsonProcessingException e) {
      throw new HabitQuestException(ErrorType.UNKNOWN_ERROR);
    }
  }



}
