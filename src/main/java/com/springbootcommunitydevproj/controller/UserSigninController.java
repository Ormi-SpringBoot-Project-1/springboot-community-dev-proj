package com.springbootcommunitydevproj.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserSigninController {

    @GetMapping("/")
    public String Signup() {
        return "signup";
    }
}
