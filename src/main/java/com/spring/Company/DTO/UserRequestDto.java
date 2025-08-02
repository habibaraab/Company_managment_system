package com.spring.Company.DTO;


import com.spring.Company.Enum.Level;
import com.spring.Company.Enum.Role;
import lombok.*;


@Data
public class UserRequestDto {

    private String name;
    private String title;
    private Role role;
    private String email;
    private String password;
    private String phone;
    private Float salaryGross;
    private Level level;
    private Integer managerId;
    private Integer departmentId;
}
