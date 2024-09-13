package com.skyes.taskapp.repository;

import com.skyes.taskapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
