package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.UserRequest;
import com.springbootcommunitydevproj.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    // 생성자 주입
    private final UserService userService;
    private final PasswordEncoder encoder;

    public UserController(UserService userService, PasswordEncoder encoder){
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostMapping("/user")
    public String signup(UserRequest request){
        userService.save(request); // 회원가입(저장)
        return "redirect:/login"; // 회원가입 처리 후 로그인 페이지로 이동
    }

    @PostMapping("/delete-account")
    public String deleteId(Authentication authentication) {
        String email = authentication.name();
        userService.deleteById(email);
        return "redirect:/logout";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/checkUsername")
    @ResponseBody
    public Map<String, Boolean> checkUsername(@RequestParam("email") String Email) {
        boolean isAvailable = !userService.existsByEmail(Email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        return response;
    }

    @GetMapping("/checkNickname")
    @ResponseBody
    public Map<String, Boolean> checkNickname(@RequestParam("nickname") String nickname) {
        boolean isAvailable = !userService.existsByNickname(nickname);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        return response;
    }

    @PostMapping("/updateInfo")
    public String updateInfo( @RequestParam("password") String password, Authentication authentication){
        String email = authentication.getName();
        String changePassword = encoder.encode(password);
        userService.updateProfile(email,changePassword);
        return "redirect:/myInformation";
    }
}