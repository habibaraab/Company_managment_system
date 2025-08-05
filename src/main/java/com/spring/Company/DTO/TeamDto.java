package com.spring.Company.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class TeamDto {
    private int id;
    private String name;
    private ManagerDto manager;
    private Set<TeamMemberDTO>members;

}
