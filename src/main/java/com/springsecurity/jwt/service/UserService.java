package com.springsecurity.jwt.service;

import com.springsecurity.jwt.entity.Role;
import com.springsecurity.jwt.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
