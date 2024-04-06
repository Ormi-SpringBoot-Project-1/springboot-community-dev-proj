package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.UserRequest;
import com.springbootcommunitydevproj.model.Level;
import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.repository.CommentRepository;
import com.springbootcommunitydevproj.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private final BCryptPasswordEncoder encoder; // 암호화

    public UserService(UserRepository userRepository, CommentRepository commentRepository, BCryptPasswordEncoder encoder){
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.encoder = encoder;
    }

    public User save(UserRequest dto) throws IllegalArgumentException {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        return userRepository.save(
                User.builder()
                        .email(dto.getEmail())
                        .password(encoder.encode(dto.getPassword()))
                        .nickname(dto.getNickname())
                        .level(new Level(5, 5, "Tier 5"))
                        .description(dto.getDescription())
                        .phoneNumber(dto.getPhoneNum())
                        .build()
        );
    }

    public void deleteById(Integer userId){
        userRepository.deleteById(userId);
    }

    public boolean existsByEmail(String Email) {
        return userRepository.existsByEmail(Email);
    }

    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public void updateProfile(String Email, String changePassword){
        Optional<User> userOptional = userRepository.findByEmail(Email);
        User user = userOptional.get();
        user.setPassword(changePassword);
        userRepository.save(user);
    }

    public Optional<User> findUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}