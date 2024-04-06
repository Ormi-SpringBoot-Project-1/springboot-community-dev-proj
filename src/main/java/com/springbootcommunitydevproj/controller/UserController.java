package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.UserRequest;
import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
    @ResponseBody
    public ResponseEntity<String> signup(@ModelAttribute UserRequest request){
        try {
            userService.save(request); // 회원가입(저장)
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getLocalizedMessage());
        }
        return ResponseEntity.ok("회원 가입 성공"); // 회원가입 처리 후 로그인 페이지로 이동
    }

    @GetMapping("/delete-account")
    public String deleteId(@AuthenticationPrincipal User user) {
        userService.deleteById(user.getId());
        return "redirect:/login";
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
    public String updateInfo( @RequestParam("password") String password, @AuthenticationPrincipal User user){
        String email = user.getEmail();
        String changePassword = encoder.encode(password);
        userService.updateProfile(email,changePassword);
        return "redirect:/myInformation";
    }

    @GetMapping("/find-email")
    public ResponseEntity<String> findUserEmailByPhoneNumber(@RequestParam String phoneNumber) {
        Optional<User> userOptional = userService.findUserByPhoneNumber(phoneNumber);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user.getEmail());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}