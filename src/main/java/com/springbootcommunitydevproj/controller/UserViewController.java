package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
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
    public String index(){
        return "/main";
    }
}