package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.UserRequest;
import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserViewController {
    private final UserRepository userRepository;

    // UserRepository 주입 받음
    public UserViewController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 로그인 페이지 요청 처리
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "message", required = false) String errorMessage,
            Model model
    ) {
        // 에러 및 에러 메시지를 모델에 추가하여 로그인 페이지로 전달
        model.addAttribute("error", error);
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    // 회원가입 페이지 요청 처리
    @GetMapping("/signup")
    public String signup(Model model){
        // 회원가입 폼을 렌더링하기 위해 빈 UserRequest 객체를 모델에 추가
        model.addAttribute("userRequest", new UserRequest());
        return "signup";
    }

    /**
     * 메인 페이지 요청 처리
     * 현재 인증된 사용자의 이메일을 가져옴
     * 이메일로 사용자 정보 조회
     * 사용자 정보를 모델에 추가
     * */
    @GetMapping("/main")
    public String main(Model model, Authentication authentication){
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);
        model.addAttribute("user", user.get());
        return "main";
    }

    // 아이디 찾기 페이지 요청 처리
    @GetMapping("/findId")
    public String findId() {
        return "findId";
    }

    // 비밀번호 찾기 페이지 요청 처리
    @GetMapping("/findPassword")
    public String findPassword() {
        return "findPassword";
    }
}