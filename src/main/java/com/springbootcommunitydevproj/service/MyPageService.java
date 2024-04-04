package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.MyPage;
import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.repository.CommentRepository;
import com.springbootcommunitydevproj.repository.MyPageRepository;
import com.springbootcommunitydevproj.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class MyPageService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final MyPageRepository myPageRepository;
    private final UserService userService;
    private final PasswordEncoder encoder;

    public MyPageService(UserRepository userRepository, CommentRepository commentRepository, MyPageRepository myPageRepository, UserService userService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.myPageRepository = myPageRepository;
        this.userService = userService;
        this.encoder = encoder;
    }

    public MyPage getUserInfo(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new MyPage(user);
        } else {
            throw new MyPageUserNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}