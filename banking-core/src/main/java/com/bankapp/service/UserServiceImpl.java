package com.bankapp.service;

import com.bankapp.entity.Role;
import com.bankapp.entity.User;
import com.bankapp.exception.DuplicateResourceException;
import com.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User register(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateResourceException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
    }
}
