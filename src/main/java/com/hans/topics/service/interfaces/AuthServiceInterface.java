package com.hans.topics.service.interfaces;

import com.hans.topics.dto.LoginRequest;
import com.hans.topics.dto.LoginResponse;
import com.hans.topics.dto.RegisterRequest;
import com.hans.topics.dto.RegisterResponse;

public interface AuthServiceInterface {

    LoginResponse login(LoginRequest loginRequest);
    RegisterResponse register(RegisterRequest registerRequest);
}
