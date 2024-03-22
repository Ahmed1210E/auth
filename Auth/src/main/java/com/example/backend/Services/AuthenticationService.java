package com.example.backend.Services;

import com.example.backend.Dao.request.SignUpRequest;
import com.example.backend.Dao.request.SigninRequest;
import com.example.backend.Dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
