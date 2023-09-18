package com.adamc.eventplannerbe.auth;

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
    private Long id;
    private String name;
    private String email;
}
