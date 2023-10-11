package com.adamc.eventplannerbe.auth;

import com.adamc.eventplannerbe.entities.User;
import com.adamc.eventplannerbe.repos.UserRepository;
import com.adamc.eventplannerbe.responses.AuthenticationResponse;
import com.adamc.eventplannerbe.responses.RefreshResponse;
import com.adamc.eventplannerbe.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public void addRefreshToCookies(String refresh, HttpServletResponse response) {
        Cookie refreshCookie = new Cookie("refresh", refresh);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("http://127.0.0.1:5173");

        response.addCookie(refreshCookie);

    }

    public AuthenticationResponse register(RegisterRequest registerRequest, HttpServletResponse response) {
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        addRefreshToCookies(refreshToken, response);

        return AuthenticationResponse.builder()
                .id(user.getId())
                .token(token)
                .refresh(refreshToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  loginRequest.getEmail(),
                  loginRequest.getPassword()
          )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        addRefreshToCookies(refreshToken, response);

        return AuthenticationResponse.builder()
                .id(user.getId())
                .token(token)
                .refresh(refreshToken)
                .build();
    }

    public RefreshResponse refresh(String oldRefreshToken, HttpServletResponse response) {
        String username = jwtService.extractRefreshUsername(oldRefreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        addRefreshToCookies(refreshToken, response);

        return RefreshResponse
                .builder()
                .token(token)
                .refresh(refreshToken)
                .build();
    }
}
