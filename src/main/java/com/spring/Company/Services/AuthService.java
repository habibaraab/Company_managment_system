package com.spring.Company.Services;

import com.spring.Company.DTO.AuthenticationResponse;
import com.spring.Company.DTO.LoginRequest;
import com.spring.Company.DTO.UserRequestDto;
import com.spring.Company.Enum.TokenType;
import com.spring.Company.Mapper.UserMapper;
import com.spring.Company.Model.Department;
import com.spring.Company.Model.Token;
import com.spring.Company.Model.User;
import com.spring.Company.Repo.DepartmentRepo;
import com.spring.Company.Repo.TokenRepository;
import com.spring.Company.Repo.UserRepository;
import com.spring.Company.Security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final  UserRepository userRepository;
    private final  UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;
    private  final JwtService jwtService;

    private  final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    private final DepartmentRepo departmentRepo;

    // This method is used to register a new user and generate a JWT token
    public AuthenticationResponse register(UserRequestDto request) {
        log.info("Attempting to register a new user with email: {}", request.getEmail());

        log.debug("Searching for department with ID: {}", request.getDepartmentId());
        Department userDepartment = departmentRepo.findById(request.getDepartmentId())
                .orElseThrow(() -> {
                    log.error("Registration failed: Department with ID {} not found.", request.getDepartmentId());
                    return new RuntimeException("Error: Department is not found.");
                });

        log.debug("Searching for manager with ID: {}", request.getManagerId());
        User manager = userRepository.findById(request.getManagerId())
                .orElseThrow(() -> {
                    log.error("Registration failed: Manager with ID {} not found.", request.getManagerId());
                    return new RuntimeException("Error: Manager is not found.");
                });

        log.debug("Mapping DTO to entity for user: {}", request.getEmail());
        User user = userMapper.toUserEntity(request);
        user.setDepartment(userDepartment);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setManager(manager);

        log.debug("Saving the new user to the database...");
        User savedUser = userRepository.save(user);
        log.info("User with email {} registered successfully with ID: {}", savedUser.getEmail(), savedUser.getId());

        Map<String, Object> claims = new HashMap<>();

        log.debug("Generating access and refresh tokens for user ID: {}", savedUser.getId());
        String accessToken = jwtService.generateToken(savedUser, claims);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, accessToken);

        log.info("Registration process completed for user: {}", request.getEmail());
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // This method is used to authenticate the user and generate a JWT token
    public AuthenticationResponse login(LoginRequest request) {
        log.info("Login attempt for user: {}", request.getEmail());

        try {
            // Authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            log.info("Authentication successful for user: {}", request.getEmail());
        } catch (Exception e) {
            log.warn("Authentication failed for user: {}. Reason: {}", request.getEmail(), e.getMessage());
            throw e;
        }

        // If authentication is successful, generate a JWT token
        log.debug("Fetching user details from repository for token generation.");
        User user = userRepository.findUserByEmail(request.getEmail());
        Map<String, Object> claims = new HashMap<>();

        log.debug("Generating new tokens for user: {}", request.getEmail());
        String accessToken = jwtService.generateToken(user, claims);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Save the token in the database
        saveUserToken(user, accessToken);

        log.info("Login process completed successfully for user: {}", request.getEmail());
        //  return two token in response
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // This method is used to save the JWT token in the database
    private void saveUserToken(User user, String jwtToken) {
        log.debug("Saving new token to database for user ID: {}", user.getId());
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
        log.debug("Token saved successfully.");
    }
}