package com.adamc.eventplannerbe.service;

import com.adamc.eventplannerbe.entities.User;
import com.adamc.eventplannerbe.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user;
    }

    public void putUser(User user) {
        userRepository.save(user);
    }
}
