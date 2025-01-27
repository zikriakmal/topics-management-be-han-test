package com.hans.topics.service;

import com.hans.topics.dto.*;
import com.hans.topics.entity.User;
import com.hans.topics.repository.UserRepository;
import com.hans.topics.security.BCrypt;
import com.hans.topics.service.interfaces.AuthServiceInterface;
import com.hans.topics.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService implements AuthServiceInterface {

    @Autowired
    ValidationService validationService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        validationService.validate(loginRequest);

        User user = userRepository
                .findByUsernameOrEmail(loginRequest.getUsername(),loginRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password wrong"));

        if (BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            UserResponse userResponse = userService.convertToUserResponse(user);
            String accessToken = jwtUtil.generateToken(userResponse);

            return LoginResponse.builder().user(userResponse).accessToken(accessToken).expiredAt(JwtUtil.EXPIRATION_TIME).build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password wrong");
        }
    }


    public RegisterResponse register(RegisterRequest registerRequest) {
        validationService.validate(registerRequest);

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);

        UserResponse userResponse = userService.convertToUserResponse(user);
        String accessToken = jwtUtil.generateToken(userResponse);

        return RegisterResponse.builder().user(userResponse).accessToken(accessToken).expiredAt(JwtUtil.EXPIRATION_TIME).build();
    }

}
