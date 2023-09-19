package com.adamc.eventplannerbe.auth;

import com.adamc.eventplannerbe.entities.User;
import com.adamc.eventplannerbe.repos.UserRepository;
import com.adamc.eventplannerbe.responses.AuthenticationResponse;
import com.adamc.eventplannerbe.responses.RefreshResponse;
import com.adamc.eventplannerbe.service.JwtService;
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

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        String jwtRefreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  loginRequest.getEmail(),
                  loginRequest.getPassword()
          )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        String jwtRefreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public RefreshResponse refresh(String oldRefreshToken) {
        String username = jwtService.extractRefreshUsername(oldRefreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return RefreshResponse
                .builder()
                .token(token)
                .refresh(refreshToken)
                .build();
    }
}
