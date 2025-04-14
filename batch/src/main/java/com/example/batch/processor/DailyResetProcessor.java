package com.example.batch.processor;

import com.example.common.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyResetProcessor implements ItemProcessor<Task, Task> {
  @Override
  public Task process(Task task) {
    if (!task.isSuccess()) {
      task.getUser().reduceHealth(task.getLevel().getReduceHealth());
    }
    task.isSuccessInit();
    return task;
  }

}
