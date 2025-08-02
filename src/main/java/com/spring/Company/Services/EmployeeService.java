package com.spring.Company.Services;

import com.spring.Company.Mapper.UserProjection;
import com.spring.Company.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private UserRepository userRepository;

    public UserProjection findUserById(int id) {
        return userRepository.findUserById(id).orElseThrow(
                ()-> new RuntimeException("no user with this id")
        );}
}
