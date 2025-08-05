package com.spring.Company.DTO;

import com.spring.Company.Enum.Level;
import com.spring.Company.Enum.Role;
import lombok.Data;

@Data
public class TeamMemberDTO {
    private int id;
    private String name;
    private String title;
    private Role role;
    private Level level;
}
