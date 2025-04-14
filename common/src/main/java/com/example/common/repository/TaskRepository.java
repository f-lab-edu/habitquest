package com.example.common.repository;

import com.example.common.common.TaskType;
import com.example.common.entity.Task;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

//  @Query("SELECT t FROM Task t LEFT JOIN FETCH t.taskTags tt LEFT JOIN FETCH tt.tag WHERE t.id = :id")
//  Optional<Task> findTaskById(Long id);
  List<Task> findAllByUserId(Long userId);

// keyset-filtering 방식 쿼리로 조회하기
  @Query(
      "select t from Task t "
//          + "left join fetch t.tagList tl "
//          + "left join fetch t.checkList cl "
          + "where t.user.id = :userId "
          + "and (:type is null or t.type = :type) "
          + "and (:cursorCreatedAt is null or "
          + "(t.createdAt < :cursorCreatedAt or "
          + "(:cursorCreatedAt = t.createdAt and t.id < :cursorTaskId)))"
          + "order by t.createdAt DESC, t.id DESC")
  List<Task> findTasksByCursor(Long userId, TaskType type, LocalDateTime cursorCreatedAt, Long cursorTaskId, Pageable pageable);

  Task findTaskById(Long id);

}
