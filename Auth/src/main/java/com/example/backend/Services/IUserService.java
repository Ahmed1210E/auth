package com.example.backend.Services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {


    boolean checkEmailExists(String email);

    UserDetailsService userDetailsService();
}
