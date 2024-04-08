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
    private final BCryptPasswordEncoder encoder; // 비밀번호 암호화를 위한 BCryptPasswordEncoder 사용

    // 생성자를 통한 의존성 주입
    public UserService(UserRepository userRepository, CommentRepository commentRepository, BCryptPasswordEncoder encoder){
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.encoder = encoder;
    }

    // 회원가입 처리 메서드
    public User save(UserRequest dto) throws IllegalArgumentException {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        // 사용자 정보 저장
        return userRepository.save(
                User.builder()
                        .email(dto.getEmail())
                        .password(encoder.encode(dto.getPassword())) // 비밀번호 암호화하여 저장
                        .nickname(dto.getNickname())
                        .level(new Level(5, 5, "Tier 5"))
                        .description(dto.getDescription())
                        .phoneNumber(dto.getPhoneNum())
                        .build()
        );
    }

    // 사용자 삭제 메서드
    public void deleteById(Integer userId){
        userRepository.deleteById(userId);
    }

    // 이메일 중복 확인 메서드
    public boolean existsByEmail(String Email) {
        return userRepository.existsByEmail(Email);
    }

    // 닉네임 중복 확인 메서드
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    // 사용자 정보 업데이트 메서드
    public void updateProfile(String Email, String changePassword){
        Optional<User> userOptional = userRepository.findByEmail(Email);
        User user = userOptional.get();
        user.setPassword(changePassword); // 비밀번호 업데이트
        userRepository.save(user);
    }

    // 전화번호로 사용자 조회 메서드
    public Optional<User> findUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}