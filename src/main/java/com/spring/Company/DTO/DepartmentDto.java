package com.spring.Company.DTO;

import lombok.Data;

@Data
public class DepartmentDto {
    private int id;
    private String name;
    private CompanyDto company;
}