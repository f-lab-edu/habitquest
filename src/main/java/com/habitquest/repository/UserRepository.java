package com.habitquest.repository;

import com.habitquest.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUserName(String userName);
  boolean existsByEmail(String email);
  Optional<User> findUserByUserNameOrEmail(String userName, String email);
}
