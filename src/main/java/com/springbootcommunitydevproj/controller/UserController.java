package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.AddUserRequest;
import com.springbootcommunitydevproj.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    // 생성자 주입
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user")
    public String signup(AddUserRequest request){
        userService.save(request); // 회원가입(저장)
        return "redirect:/login"; // 회원가입 처리 후 로그인 페이지로 이동
    }

    @PostMapping("/delete-account")
    public String deleteId(Authentication authentication) {
        String userId = authentication.name();
        userService.deleteById(userId);
        return "redirect:/logout";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}