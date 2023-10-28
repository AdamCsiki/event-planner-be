package com.adamc.eventplannerbe.auth;

import com.adamc.eventplannerbe.requests.LoginRequest;
import com.adamc.eventplannerbe.requests.RegisterRequest;
import com.adamc.eventplannerbe.responses.AuthenticationResponse;
import com.adamc.eventplannerbe.responses.OnlineResponse;
import com.adamc.eventplannerbe.responses.RefreshResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        AuthenticationResponse authResponse = authService.register(registerRequest, response);

        if(authResponse == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(loginRequest, response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@CookieValue(name = "refresh") String refreshToken, HttpServletResponse response) {
        return ResponseEntity.ok(authService.refresh(refreshToken, response));
    }

    @GetMapping("/online")
    public ResponseEntity<OnlineResponse> isOnline() {
        OnlineResponse onlineResponse = new OnlineResponse(true);

        return ResponseEntity.ok(onlineResponse);
    }

}
