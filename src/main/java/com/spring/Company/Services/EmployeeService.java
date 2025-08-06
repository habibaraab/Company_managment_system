package com.spring.Company.Services;

import com.spring.Company.Mapper.UserProjection;
import com.spring.Company.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final UserRepository userRepository;

    public UserProjection findUserById(int id) {
        log.info("Attempting to find employee projection for ID: {}", id);
        log.debug("Calling repository method findUserById for ID: {}", id);
        Optional<UserProjection> userProjectionOptional = userRepository.findUserById(id);

        if (userProjectionOptional.isPresent()) {
            log.info("Successfully found employee projection for ID: {}", id);
            return userProjectionOptional.get();
        } else {
            log.error("Employee not found with ID: {}. Throwing RuntimeException.", id);
            throw new RuntimeException("no user with this id");
        }
    }
}