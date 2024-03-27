package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.domain.User;
import com.springbootcommunitydevproj.dto.AddUserRequest;
import com.springbootcommunitydevproj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public User save(AddUserRequest request) {
        return userRepository.save(new User(
                request.getEmail(), encoder.encode(request.getPassword())), );
    }
}