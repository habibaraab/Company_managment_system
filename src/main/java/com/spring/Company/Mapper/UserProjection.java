package com.spring.Company.Mapper;

import com.spring.Company.Enum.Level;

public interface UserProjection {
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