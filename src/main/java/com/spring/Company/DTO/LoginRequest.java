package com.spring.Company.DTO;


import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String email;
    private String password;

}
