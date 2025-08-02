package com.spring.Company.Services;

import com.spring.Company.DTO.UserDto;
import com.spring.Company.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private UserRepository userRepository;

    public UserDto findUserById(int id) {
        return userRepository.findUserById(id).orElseThrow(
                ()-> new RuntimeException("no user with this id")
        );}
}
