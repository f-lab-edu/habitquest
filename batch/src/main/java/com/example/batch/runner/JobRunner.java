package com.example.batch.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobRunner implements CommandLineRunner {
  private final JobLauncher jobLauncher;
  private final Job dailyResetJob;

  @Override
  public void run(String... args) throws Exception {
    JobParameters params = new JobParametersBuilder()
        .addLong("run.id", System.currentTimeMillis())
        .toJobParameters();

    jobLauncher.run(dailyResetJob, params);
    log.info("dailyResetJob run success");
  }
}
