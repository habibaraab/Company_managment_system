package com.spring.Company.Services;


import com.spring.Company.DTO.UserRequestDto;
import com.spring.Company.DTO.UserResponseDto;
import com.spring.Company.Mapper.UserMapper;
import com.spring.Company.Model.Department;
import com.spring.Company.Model.User;

import com.spring.Company.Repo.DepartmentRepo;
import com.spring.Company.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManagerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepo departmentRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto findUserById(int id){
        User user=userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"+id));

                return userMapper.toUserResponseDto(user);

    }

    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = userMapper.toUserEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Department department = departmentRepository.findById(requestDto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + requestDto.getDepartmentId()));
        user.setDepartment(department);


            User manager = userRepository.findById(requestDto.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + requestDto.getManagerId()));
            user.setManager(manager);


        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }

    public void deleteUser(int id){
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }

    public UserResponseDto updateUser(int userId, UserRequestDto requestDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (requestDto.getName() != null) existingUser.setName(requestDto.getName());
        if (requestDto.getTitle() != null) existingUser.setTitle(requestDto.getTitle());
        if (requestDto.getRole() != null) existingUser.setRole(requestDto.getRole());
        if (requestDto.getEmail() != null) existingUser.setEmail(requestDto.getEmail());
        if (requestDto.getPhone() != null) existingUser.setPhone(requestDto.getPhone());
        if (requestDto.getLevel() != null) existingUser.setLevel(requestDto.getLevel());
        if(requestDto.getSalaryGross()!=null) existingUser.setSalaryGross(requestDto.getSalaryGross());
        if(requestDto.getPassword()!=null)
            existingUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        if (requestDto.getManagerId() != null) {

            User manager = userRepository.findById(requestDto.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found with id: " + requestDto.getManagerId()));
            existingUser.setManager(manager);


        }
        if (requestDto.getDepartmentId() != null){

            Department department = departmentRepository.findById(requestDto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + requestDto.getDepartmentId()));
            existingUser.setDepartment(department);

        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toUserResponseDto(updatedUser);
    }




}
