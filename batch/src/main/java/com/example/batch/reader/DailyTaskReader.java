//package com.example.batch.reader;
//
//import com.example.common.common.TaskType;
//import com.example.common.entity.Task;
//import jakarta.persistence.EntityManagerFactory;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.item.database.JpaPagingItemReader;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DailyTaskReader extends JpaPagingItemReader<Task> {
//
//  public DailyTaskReader(EntityManagerFactory entityManagerFactory) {
//    this.setName("dailyTaskReader");
//    this.setEntityManagerFactory(entityManagerFactory);
//    this.setPageSize(10);
//    this.setQueryString("select t from Task t join fetch t.user where t.type = :type");
//    this.setParameterValues(Map.of("type", TaskType.DAILY));
//
//  }
//
//
//}
