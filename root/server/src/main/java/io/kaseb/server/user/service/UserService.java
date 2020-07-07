package io.kaseb.server.user.service;

import io.kaseb.server.user.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public UserEntity signup(String username, String hashedPassword) {
        return null;
    }

    public UserEntity login(String username, String hashedPassword) {
        return null;
    }
}
