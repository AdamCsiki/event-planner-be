package com.adamc.eventplannerbe.controllers;

import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.entities.User;
import com.adamc.eventplannerbe.requests.AddProjectRequest;
import com.adamc.eventplannerbe.service.JwtService;
import com.adamc.eventplannerbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    /**
     * GET REQUESTS
     */
    @GetMapping("/user/token")
    public ResponseEntity<User> getUserByToken(@CookieValue(name = "refresh") String refreshToken) {
        String email = jwtService.extractRefreshUsername(refreshToken);

        User user = userService.getUserByEmail(email).orElseThrow();

        user.setPassword("");

        return ResponseEntity.ok(user);
    }


}
