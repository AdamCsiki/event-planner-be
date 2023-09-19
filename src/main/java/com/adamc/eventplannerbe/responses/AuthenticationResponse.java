package com.adamc.eventplannerbe.responses;

import com.adamc.eventplannerbe.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String refreshToken;

    private Long id;
    private String name;
    private String email;
}
