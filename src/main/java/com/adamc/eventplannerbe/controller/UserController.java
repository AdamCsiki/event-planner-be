package com.adamc.eventplannerbe.controller;

import com.adamc.eventplannerbe.auth.AuthService;
import com.adamc.eventplannerbe.entities.User;
import com.adamc.eventplannerbe.repos.UserRepository;
import com.adamc.eventplannerbe.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @GetMapping("/user/token")
    public ResponseEntity<User> getUserByToken(@CookieValue(name = "refresh") String refreshToken) {
        String email = jwtService.extractRefreshUsername(refreshToken);

        System.out.println(email);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with username " + email + " not found."));

        user.setPassword("");

        return ResponseEntity.ok(user);
    }
}
