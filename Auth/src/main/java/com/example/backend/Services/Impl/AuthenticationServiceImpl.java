package com.example.backend.Services.Impl;

import com.example.backend.Dao.AppUserRepo;
import com.example.backend.Dao.request.SignUpRequest;
import com.example.backend.Dao.request.SigninRequest;
import com.example.backend.Dao.response.JwtAuthenticationResponse;
import com.example.backend.Entities.AppUser;
import com.example.backend.Entities.Rolee;
import com.example.backend.Services.AuthenticationService;
import com.example.backend.Services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private String role;
    @Autowired
    private  AppUserRepo userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {

        var user = AppUser.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Rolee.Admin).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken( user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}