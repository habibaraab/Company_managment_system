package com.spring.Company.Mapper;


import com.spring.Company.DTO.CompanyDto;
import com.spring.Company.Model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto toDto(Company company);
    Company toEntity(CompanyDto companyDto);
}