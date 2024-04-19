package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.UserRequest;
import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@Controller
@Tag(name = "회원 로그인, 회원 가입 관련 컨트롤러")
public class UserController {
    /**
     * UserService와 PasswordEncoder를 생성자 주입받음
     */
    private final UserService userService;
    private final PasswordEncoder encoder;

    // 생성자를 통한 의존성 주입
    public UserController(UserService userService, PasswordEncoder encoder){
        this.userService = userService;
        this.encoder = encoder;
    }

    /**
     * 회원가입 처리를 담당하는 메서드
     * UserService를 사용하여 회원정보 저장
     */
    @PostMapping("/user")
    @ResponseBody
    @Operation(summary = "회원 가입", description = "회원 가입할 때 사용하는 API입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "443", description = "회원 가입 도중 문제 발생 (이미 사용중인 이메일 혹은 닉네임)", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> signup(@Parameter(name = "회원 가입 폼 데이터", required = true) @ModelAttribute UserRequest request){
        try {
            userService.save(request); // 회원가입(저장)
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getLocalizedMessage());
        }
        return ResponseEntity.ok("회원 가입 성공"); // 회원가입 처리 후 로그인 페이지로 이동
    }

    /**
     * 계정 삭제 처리를 담당하는 메서드
     * UserService를 사용하여 계정 삭제
     * 로그인 페이지로 리다이렉트
     */
    @GetMapping("/delete-account")
    public String deleteId(@AuthenticationPrincipal User user) {
        userService.deleteById(user.getId());
        return "redirect:/login";
    }

    /**
     * 로그아웃 처리를 담당하는 메서드
     * Spring Security를 사용하여 로그아웃 처리
     * 로그인 페이지로 리다이렉트
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    /**
     * 이메일 중복 체크를 담당하는 메서드
     * UserService를 사용하여 이메일 중복 확인
     */
    @GetMapping("/checkUsername")
    @ResponseBody
    @Operation(summary = "이메일 중복 체크", description = "회원 가입 폼 데이터에서 이메일이 중복된 이메일인지 확인하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "이메일 중복 체크 결과", content = @Content(mediaType = "application/json"))
    public Map<String, Boolean> checkUsername(@RequestParam("email") String Email) {
        boolean isAvailable = !userService.existsByEmail(Email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        return response;
    }

    /**
     * 닉네임 중복 체크를 담당하는 메서드
     * UserService를 사용하여 닉네임 중복 확인
     */
    @GetMapping("/checkNickname")
    @ResponseBody
    @Operation(summary = "닉네임 중복 체크", description = "회원 가입 폼 데이터에서 닉네임이 중복된 닉네임인지 확인하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "닉네임 중복 체크 결과", content = @Content(mediaType = "application/json"))
    public Map<String, Boolean> checkNickname(@RequestParam("nickname") String nickname) {
        boolean isAvailable = !userService.existsByNickname(nickname);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        return response;
    }

    /**
     * 사용장 정보 업데이트를 담당하는 메서드
     * 입력 받은 비밀번호를 인코딩
     * UserService를 사용하여 사용자 정보 업데이트
     * 내 정보 페이지로 리다이렉트
     */
    @PostMapping("/updateInfo")
    public String updateInfo( @RequestParam("password") String password, @AuthenticationPrincipal User user){
        String email = user.getEmail();
        String changePassword = encoder.encode(password);
        userService.updateProfile(email,changePassword);
        return "redirect:/myInformation";
    }

    /**
     * 전화번호로 이메일 찾기를 담당하는 메서드
     * UserService를 사용하여 전화번호로 사용자 정보 조회
     * 사용자 이메일을 응답
     * 사용자를 찾지 못한 경우 404 응답
     */
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