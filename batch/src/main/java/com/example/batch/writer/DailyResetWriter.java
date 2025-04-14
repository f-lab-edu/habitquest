package com.example.batch.writer;

import com.example.common.entity.Task;
import com.example.common.entity.User;
import com.example.common.repository.TaskRepository;
import com.example.common.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyResetWriter implements ItemWriter<Task> {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;

  @Override
  public void write(Chunk<? extends Task> chunk) {
    List<? extends Task> tasks = chunk.getItems();

    taskRepository.saveAll(tasks);

    // 중복 제거된 user 목록만 저장
    List<User> users = tasks.stream().map(Task::getUser).distinct().toList();
    userRepository.saveAll(users);
  }


}
