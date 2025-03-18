package com.habitquest.repository;

import com.habitquest.entity.Habit;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
  Optional<Habit> findHabitById(Long id);
  List<Habit> findAllByUserId(Long userId);
}
