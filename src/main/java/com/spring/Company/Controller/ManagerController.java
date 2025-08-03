package com.spring.Company.Controller;


import com.spring.Company.DTO.UserRequestDto;
import com.spring.Company.DTO.UserResponseDto;
import com.spring.Company.Model.User;
import com.spring.Company.Services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins="*")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable int id) {
        UserResponseDto user=managerService.findUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto createdUser = managerService.createUser(requestDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }



    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable int id, @RequestBody UserRequestDto requestDto) {
        UserResponseDto updatedUser = managerService.updateUser(id, requestDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }




    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        managerService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
