package com.example.batch.config;

import com.example.batch.processor.DailyResetProcessor;
import com.example.batch.writer.DailyResetWriter;
import com.example.common.common.TaskType;
import com.example.common.entity.Task;
import jakarta.persistence.EntityManagerFactory;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DailyResetJobConfig {
  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;
  private final DailyResetProcessor dailyResetProcessor;
  private final DailyResetWriter dailyResetWriter;
  private final EntityManagerFactory entityManagerFactory;


  @Bean
  public Job dailyResetJob() {
    return new JobBuilder("dailyResetJob", jobRepository)
        .start(resetDailyStep())
        .build();
  }

  @Bean
  public Step resetDailyStep() {
    return new StepBuilder("resetDailyStep", jobRepository)
        .<Task, Task>chunk(10, transactionManager) // 내부에서 데이터를 몇개씩 묶어서 처리할지 정하는 단위
        .reader(dailyTaskReader()) // 데이터를 읽는 컴포넌트 정의
        .processor(dailyResetProcessor) // 데이터 가공/처리 컴포넌트
        .writer(dailyResetWriter) // 처리된 데이터 저장 컴포넌트
        .build();
  }

  @Bean
  public JpaPagingItemReader<Task> dailyTaskReader() {
    JpaPagingItemReader<Task> reader = new JpaPagingItemReader<>();
    reader.setName("dailyTaskReader");
    reader.setEntityManagerFactory(entityManagerFactory);
    reader.setPageSize(10);
    reader.setQueryString("SELECT t FROM Task t WHERE t.type = :type");
    reader.setParameterValues(Map.of("type", TaskType.DAILY));
    return reader;
  }
}
