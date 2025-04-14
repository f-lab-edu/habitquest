package com.example.common.repository;

import com.example.common.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUserName(String userName);
  boolean existsByEmail(String email);
  Optional<User> findUserByUserNameOrEmail(String userName, String email);
  Optional<User> findUserById(Long id);
}
