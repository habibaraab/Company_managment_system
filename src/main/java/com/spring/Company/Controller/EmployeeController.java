package com.spring.Company.Controller;

import com.spring.Company.Mapper.UserProjection;
import com.spring.Company.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins="*")
public class EmployeeController {
    @Autowired
   private EmployeeService employeeService;


    @GetMapping("/{id}")
    public ResponseEntity<UserProjection>findUserById(@PathVariable int id){
        UserProjection user = employeeService.findUserById(id);
        return ResponseEntity.ok(user);
    }
}
