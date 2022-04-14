package com.tienminh.springsecurity.repositories;

import com.tienminh.springsecurity.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User getUserModelByUsername(String name);
}
