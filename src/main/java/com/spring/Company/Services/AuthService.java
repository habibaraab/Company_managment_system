package com.spring.Company.Services;


import com.spring.Company.DTO.AuthenticationResponse;
import com.spring.Company.DTO.LoginRequest;
import com.spring.Company.DTO.RegisterRequest;
import com.spring.Company.Enum.TokenType;
import com.spring.Company.Model.Token;
import com.spring.Company.Model.User;
import com.spring.Company.Repo.TokenRepository;
import com.spring.Company.Repo.UserRepository;
import com.spring.Company.Security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    // This method is used to register a new user and generate a JWT token
    public AuthenticationResponse register(RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setLevel(request.getLevel());
        user.setTitle(request.getTitle());
        user.setPhone(request.getPhone());
        user.setSalaryGross(request.getSalaryGross());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();

        String accessToken = jwtService.generateToken(savedUser, claims);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, accessToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // This method is used to authenticate the user and generate a JWT token
    public AuthenticationResponse login(LoginRequest request){

        // Authenticate the user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        // If authentication is successful, generate a JWT token
        User user = userRepository.findUserByEmail(request.getEmail());
        Map<String, Object> claims = new HashMap<>();

        String accessToken = jwtService.generateToken(user,claims);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Save the token in the database
        saveUserToken(user, accessToken);

        //  return two token in response
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // This method is used to save the JWT token in the database
    private void saveUserToken(User user, String jwtToken) {

        // Create a new Token object
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        // Save the token in the database
        tokenRepository.save(token);
    }
}