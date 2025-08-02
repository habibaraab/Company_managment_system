package com.spring.Company.Controller;

import com.spring.Company.Mapper.UserProjection;
import com.spring.Company.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
   private EmployeeService employeeService;


    @GetMapping("/{id}")
    public ResponseEntity<UserProjection>findUserById(@PathVariable int id){
        UserProjection user = employeeService.findUserById(id);
        return ResponseEntity.ok(user);
    }
}
