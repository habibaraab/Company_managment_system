package com.spring.Company.Mapper;

import com.spring.Company.DTO.ManagerDto;
import com.spring.Company.DTO.UserRequestDto;
import com.spring.Company.DTO.UserResponseDto;
import com.spring.Company.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface UserMapper {

    UserResponseDto toUserResponseDto(User user);
    ManagerDto toManagerDto(User user);


    User toUserEntity(UserRequestDto requestDto);
}