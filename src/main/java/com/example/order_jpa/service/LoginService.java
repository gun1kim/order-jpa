package com.example.order_jpa.service;

import com.example.order_jpa.dto.UserLoginDto;
import com.example.order_jpa.entity.User;
import com.example.order_jpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(UserLoginDto userLoginDto) {
        Optional<User> byEmail = userRepository.findByEmail(userLoginDto.getEmail());
        if (!byEmail.isEmpty()) {
            User user = byEmail.get();
            if (user.getPassword().equals(userLoginDto.getPassword())) {
                return user;
            }
        }
        return null;
    }

}