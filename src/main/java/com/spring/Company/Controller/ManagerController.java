package com.spring.Company.Controller;


import com.spring.Company.Model.User;
import com.spring.Company.Model.UserHistory;
import com.spring.Company.Services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/user/{id}")
    public User findById(@PathVariable int id) {
        return managerService.findUserById(id);
    }

    @PostMapping("/addUser")
    public void createUser(@RequestBody User user) {
        managerService.addUser(user);
    }

    @PutMapping("/updateUser/{id}")
    public void upadteUser(@PathVariable int id,@RequestBody User user) {
        managerService.updateUser(id, user);
    }

    @DeleteMapping("/deleteUser/id")
    public void deleteUser(@PathVariable int id) {
        managerService.deleteUser(id);
    }

    @GetMapping("/users/{id}/history")
    public ResponseEntity<List<UserHistory>> getUserHistory(@PathVariable int id) {
        List<UserHistory> history = managerService.getUserHistory(id);
        return ResponseEntity.ok(history);
    }

}
