package com.spring.Company.DTO;

import com.spring.Company.Enum.Level;

public interface UserDto {
    int getId();

    String getName();

    String getTitle();

    String getRole();

    String getMail();

    String getPhone();

    Level getLevel();

    Integer getManagerId();

    Integer getDepartmentId();

}