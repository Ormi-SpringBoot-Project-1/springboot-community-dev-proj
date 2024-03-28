package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.AddUserRequest;
import com.springbootcommunitydevproj.entity.User;
import com.springbootcommunitydevproj.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder encoder; // 암호화

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User save(AddUserRequest dto){
        return userRepository.save(
                User.builder()
                        .id(dto.getId())
                        .nickname(dto.getNickname())
                        .email(dto.getEmail())
                        .password(encoder.encode(dto.getPassword()))
                        .description(dto.getDescription())
                        .levelId(dto.getLevelId())
                        .reportedCount(dto.getReportedCount())
                        .createdAt(dto.getCreatedAt())
                        .isAdmin(dto.getIsAdmin())
                        .userIp(dto.getUserIp())
                        .lastLogInIp(dto.getLastLogInIp())
                        .phoneNumber(dto.getPhoneNumber())
                        .build()
        );
    }

    public void deleteById(String userId){
        userRepository.deleteById(Integer.valueOf(userId));
    }
}