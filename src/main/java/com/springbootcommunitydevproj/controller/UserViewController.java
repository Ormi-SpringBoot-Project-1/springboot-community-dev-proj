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

    public UserViewController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "message", required = false) String errorMessage,
            Model model
    ) {
        model.addAttribute("error", error);
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("userRequest", new UserRequest());
        return "signup";
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