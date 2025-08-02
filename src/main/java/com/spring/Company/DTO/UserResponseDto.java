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
public class UserResponseDto {
    private int id;
    private String name;
    private String title;
    private Role role;
    private String email;
    private String phone;
    private Float salaryGross;
    private Float salaryNet;
    private Level level;
    private ManagerDto manager;
    private DepartmentDto department;
}
