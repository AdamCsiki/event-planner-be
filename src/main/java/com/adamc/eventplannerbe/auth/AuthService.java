package com.adamc.eventplannerbe.auth;

import com.adamc.eventplannerbe.entities.User;
import com.adamc.eventplannerbe.repos.UserRepository;
import com.adamc.eventplannerbe.requests.LoginRequest;
import com.adamc.eventplannerbe.requests.RegisterRequest;
import com.adamc.eventplannerbe.responses.AuthenticationResponse;
import com.adamc.eventplannerbe.responses.RefreshResponse;
import com.adamc.eventplannerbe.service.JwtService;
import com.adamc.eventplannerbe.validator.UserValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    private UserValidator userValidator = new UserValidator();

    public void addRefreshToCookies(String refresh, HttpServletResponse response) {
//        Cookie refreshCookie = new Cookie("refresh", refresh);
//        refreshCookie.setHttpOnly(true);
//        refreshCookie.setSecure(true); // doesn't work on localhost
//        refreshCookie.setPath("/");
//
//        response.addCookie(refreshCookie);

        response.setHeader("Set-Cookie", "refresh=" + refresh + "; path=/; HttpOnly; SameSite=None; Secure;");
    }

    public AuthenticationResponse register(RegisterRequest registerRequest, HttpServletResponse response) {
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        if(!userValidator.isUserValid(user)) {
            return new AuthenticationResponse();
        }

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

        if(!jwtService.isRefreshTokenValid(oldRefreshToken, userDetails)) {
            return RefreshResponse.builder().token("").refresh("").build();
        }

        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        addRefreshToCookies(refreshToken, response);

        return RefreshResponse
                .builder()
                .token(token)
                .refresh(refreshToken)
                .build();
    }

    public RefreshResponse refreshDebug(String oldToken, HttpServletResponse response) {
        String username = jwtService.extractUsername(oldToken);

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
