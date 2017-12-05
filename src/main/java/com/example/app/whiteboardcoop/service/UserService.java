package com.example.app.whiteboardcoop.service;

import com.example.app.whiteboardcoop.model.User;

import java.util.Optional;

public interface UserService extends DefaultService<User> {
    Optional<User> findByUsername(String username);
}
