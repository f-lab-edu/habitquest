//package com.example.batch.scheduler;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class BatchScheduler {
//
//  private final JobLauncher jobLauncher;
//  private final Job dailyResetJob;
//
//  @Scheduled(cron = "0 */52 * * * *")
////  @Scheduled(fixedDelay = 2 * 60 * 1000)
////  @Scheduled(cron = "0 0 0 * * ?")
//  public void runDailyResetJob() throws Exception {
//
//    JobParameters params = new JobParametersBuilder().addLong("run.id", System.currentTimeMillis())
//        .toJobParameters();
//    jobLauncher.run(dailyResetJob, params);
//    log.info("scheduler run success!");
//
//  }
//
//}
