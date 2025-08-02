package com.spring.Company.Services;


import com.spring.Company.Model.User;
import com.spring.Company.Model.UserHistory;
import com.spring.Company.Repo.UserHistoryRepository;
import com.spring.Company.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    public User findUserById(int id){
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found"));
    }

    public     List<UserHistory> getUserHistory(int id){
        return userHistoryRepository.getUserHistoryById(id);
    }




    public void addUser(User user){
        if(userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("user already exists");
        }
        userRepository.save(user);
    }

    public void deleteUser(int id){
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }


    public void updateUser(int id,User updatedUser){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exist in the database"));
        if (updatedUser.getName() != null) existingUser.setName(updatedUser.getName());
        if (updatedUser.getTitle() != null) existingUser.setTitle(updatedUser.getTitle());
        if (updatedUser.getRole() != null) existingUser.setRole(updatedUser.getRole());
        if (updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getPhone() != null) existingUser.setPhone(updatedUser.getPhone());
        if (updatedUser.getLevel() != null) existingUser.setLevel(updatedUser.getLevel());
        if (updatedUser.getManager() != null) existingUser.setManager(updatedUser.getManager());
        if (updatedUser.getDepartment() != null) existingUser.setDepartment(updatedUser.getDepartment());
        if(updatedUser.getSalaryGross()!=null) existingUser.setSalaryGross(updatedUser.getSalaryGross());

        userRepository.save(existingUser);
    }



}
