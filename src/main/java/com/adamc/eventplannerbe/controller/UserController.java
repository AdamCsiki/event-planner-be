package com.adamc.eventplannerbe.controller;

import com.adamc.eventplannerbe.auth.AuthService;
import com.adamc.eventplannerbe.entities.Project;
import com.adamc.eventplannerbe.entities.User;
import com.adamc.eventplannerbe.repos.UserRepository;
import com.adamc.eventplannerbe.requests.AddProjectRequest;
import com.adamc.eventplannerbe.service.JwtService;
import com.adamc.eventplannerbe.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/user/token")
    public ResponseEntity<User> getUserByToken(@CookieValue(name = "refresh") String refreshToken) {
        String email = jwtService.extractRefreshUsername(refreshToken);

        User user = userService.getUserByEmail(email).orElseThrow();

        user.setPassword("");

        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/project")
    public ResponseEntity<User> putProject(@RequestBody AddProjectRequest addProjectRequest, Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow();

        Project newProject = Project
                    .builder()
                    .name(addProjectRequest.getName())
                    .deadLine(addProjectRequest.getDeadLine())
                    .build();

        user.getProjects().add(newProject);

        userService.putUser(user);

        return ResponseEntity.ok(userService.getUserByEmail(principal.getName()).orElseThrow());
    }
}
