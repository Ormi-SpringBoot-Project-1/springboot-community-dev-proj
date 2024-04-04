package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.repository.UserRepository;
import com.springbootcommunitydevproj.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class UserViewController {
    private final UserRepository userRepository;

    public UserViewController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "/signup";
    }

    @GetMapping("/main")
    public String main(Model model, Authentication authentication){
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);
        model.addAttribute("user", user.get());
        return "main";
    }

    @GetMapping("/findId")
    public String findId() {
        return "findId";
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return "findPassword";
    }
}