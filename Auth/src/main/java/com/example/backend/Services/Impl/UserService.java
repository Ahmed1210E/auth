package com.example.backend.Services.Impl;

import com.example.backend.Dao.AppUserRepo;
import com.example.backend.Services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {


    @Autowired
    private  AppUserRepo userRepository;

    @Override
    public boolean checkEmailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("L'email existe déjà.");

        }return true;
    }
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

}

