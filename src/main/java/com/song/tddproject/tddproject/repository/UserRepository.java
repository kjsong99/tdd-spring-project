package com.song.tddproject.tddproject.repository;

import com.song.tddproject.tddproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByUsername(String username);
}
