package com.spring.Company.DTO;


import com.spring.Company.Enum.Level;
import com.spring.Company.Enum.Role;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {

    private int id;
    private String name;
    private String password;
    private Role role;
    private String email;
    private String phone;
    private String title;
    private Level level;
    private Float salaryGross;
}
