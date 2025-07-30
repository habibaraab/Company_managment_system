package com.spring.Company.DTO;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    private String accessToken;
    private String refreshToken;
}

