package com.spring.Company.Controller;

import com.spring.Company.DTO.AuthenticationResponse;
import com.spring.Company.DTO.LoginRequest;
import com.spring.Company.DTO.UserRequestDto;
import com.spring.Company.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins="*")
public class authController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest , @RequestParam Map<String,Object> claims) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRequestDto registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }
}