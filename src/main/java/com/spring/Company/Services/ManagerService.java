package com.spring.Company.Services;

import com.spring.Company.DTO.UserRequestDto;
import com.spring.Company.DTO.UserResponseDto;
import com.spring.Company.Mapper.UserMapper;
import com.spring.Company.Model.Department;
import com.spring.Company.Model.User;
import com.spring.Company.Repo.DepartmentRepo;
import com.spring.Company.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ManagerService {
    private final UserRepository userRepository;

    private final DepartmentRepo departmentRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto findUserById(int id) {
        log.info("Attempting to find user by ID: {}", id);
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            log.info("Successfully found user with ID: {}", id);
            return userMapper.toUserResponseDto(userOptional.get());
        } else {
            log.error("User not found with ID: {}. Throwing EntityNotFoundException.", id);
            throw new EntityNotFoundException("User Not Found with id: " + id);
        }
    }

    public UserResponseDto createUser(UserRequestDto requestDto) {
        log.info("Attempting to create a new user with email: {}", requestDto.getEmail());

        log.debug("Mapping DTO to entity and encoding password for email: {}", requestDto.getEmail());
        User user = userMapper.toUserEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        log.debug("Searching for department with ID: {}", requestDto.getDepartmentId());
        Department department = departmentRepository.findById(requestDto.getDepartmentId())
                .orElseThrow(() -> {
                    log.error("Create user failed: Department with ID {} not found.", requestDto.getDepartmentId());
                    return new EntityNotFoundException("Department not found with id: " + requestDto.getDepartmentId());
                });
        user.setDepartment(department);

        log.debug("Searching for manager with ID: {}", requestDto.getManagerId());
        User manager = userRepository.findById(requestDto.getManagerId())
                .orElseThrow(() -> {
                    log.error("Create user failed: Manager with ID {} not found.", requestDto.getManagerId());
                    return new EntityNotFoundException("Manager not found with id: " + requestDto.getManagerId());
                });
        user.setManager(manager);

        log.debug("Saving new user with email: {}", requestDto.getEmail());
        User savedUser = userRepository.save(user);
        log.info("Successfully created user with email {} and assigned ID: {}", savedUser.getEmail(), savedUser.getId());

        return userMapper.toUserResponseDto(savedUser);
    }

    public void deleteUser(int id) {
        log.info("Attempting to delete user with ID: {}", id);

        log.debug("Checking if user with ID {} exists.", id);
        if (!userRepository.existsById(id)) {
            log.error("Delete failed: User with ID {} does not exist. Throwing IllegalArgumentException.", id);
            throw new IllegalArgumentException("User with ID " + id + " does not exist");
        }

        userRepository.deleteById(id);
        log.info("Successfully deleted user with ID: {}", id);
    }

    public UserResponseDto updateUser(int userId, UserRequestDto requestDto) {
        log.info("Attempting to update user with ID: {}", userId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Update failed: User with ID {} not found.", userId);
                    return new EntityNotFoundException("User not found with id: " + userId);
                });

        log.debug("Applying updates to user entity for ID: {}", userId);
        if (requestDto.getName() != null) existingUser.setName(requestDto.getName());
        if (requestDto.getTitle() != null) existingUser.setTitle(requestDto.getTitle());
        if (requestDto.getRole() != null) existingUser.setRole(requestDto.getRole());
        if (requestDto.getEmail() != null) existingUser.setEmail(requestDto.getEmail());
        if (requestDto.getPhone() != null) existingUser.setPhone(requestDto.getPhone());
        if (requestDto.getLevel() != null) existingUser.setLevel(requestDto.getLevel());
        if (requestDto.getSalaryGross() != null) existingUser.setSalaryGross(requestDto.getSalaryGross());
        if (requestDto.getPassword() != null) {
            log.debug("Updating password for user ID: {}", userId);
            existingUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }
        if (requestDto.getManagerId() != null) {
            log.debug("Updating manager for user ID: {}. Searching for new manager ID: {}", userId, requestDto.getManagerId());
            User manager = userRepository.findById(requestDto.getManagerId())
                    .orElseThrow(() -> {
                        log.error("Update failed for user {}: New manager with ID {} not found.", userId, requestDto.getManagerId());
                        return new EntityNotFoundException("Manager not found with id: " + requestDto.getManagerId());
                    });
            existingUser.setManager(manager);
        }
        if (requestDto.getDepartmentId() != null) {
            log.debug("Updating department for user ID: {}. Searching for new department ID: {}", userId, requestDto.getDepartmentId());
            Department department = departmentRepository.findById(requestDto.getDepartmentId())
                    .orElseThrow(() -> {
                        log.error("Update failed for user {}: New department with ID {} not found.", userId, requestDto.getDepartmentId());
                        return new EntityNotFoundException("Department not found with id: " + requestDto.getDepartmentId());
                    });
            existingUser.setDepartment(department);
        }

        log.debug("Saving updated user with ID: {}", userId);
        User updatedUser = userRepository.save(existingUser);
        log.info("Successfully updated user with ID: {}", updatedUser.getId());

        return userMapper.toUserResponseDto(updatedUser);
    }
}