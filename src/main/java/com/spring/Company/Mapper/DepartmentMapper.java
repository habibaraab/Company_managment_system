package com.spring.Company.Mapper;

import com.spring.Company.DTO.DepartmentDto;
import com.spring.Company.Model.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CompanyMapper.class)
public interface DepartmentMapper {
    DepartmentDto toDto(Department department);
    Department toEntity(DepartmentDto departmentDto);
}