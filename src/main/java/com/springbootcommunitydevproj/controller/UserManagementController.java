package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.dto.UserManagementResponse;
import com.springbootcommunitydevproj.dto.UserRequest;
import com.springbootcommunitydevproj.service.UserManagementService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    // Admin 페이지의 회원 관리 페이지에 보여질 리스트 목록을 가져옵니다.
    // Query String의 page 변수를 통해 페이징됩니다.
    // 한 페이지 당 최대 30개의 결과를 가져옵니다.
    @GetMapping("/api/admin/user_list")
    public ResponseEntity<List<UserManagementInfoDto>> getAllUserManagementInfo(
        @RequestParam(name = "page", defaultValue = "1") Integer page) {
        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(userManagementService.getAllUserManagementInfo(page));
    }

    // Admin 페이지의 회원 관리 페이지에서 특정 회원의 닉네임을 검색했을 때 결과를 가져옵니다.
    // 리스트 형태로 Response를 반환합니다.
    @GetMapping("/api/admin/user/{nickname}")
    public ResponseEntity<List<UserManagementInfoDto>> getUserManagementInfoByNickname(
        @PathVariable(name = "nickname") String nickname) {
        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(userManagementService.getUserManagementInfoByNickname(nickname));
    }

    // Admin 페이지의 특정 회원의 등급을 변경합니다.
    // 변경 결과에 따라 메시지를 Response로 반환합니다.
    @PutMapping("/api/admin/user/level")
    public ResponseEntity<UserManagementResponse> changeUserLevel(@RequestBody UserRequest request) {
        UserManagementResponse response = new UserManagementResponse(userManagementService.changeUserLevel(request));

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(response);
    }

    // Admin 페이지의 특정 회원의 가입 제한을 설정합니다.
    // 변경 결과에 따라 메시지를 Response로 반환합니다.
    @PostMapping("/api/admin/user/blocked")
    public ResponseEntity<UserManagementResponse> setUserToBlockedUser(@RequestBody UserRequest request) {
        UserManagementResponse response = new UserManagementResponse(userManagementService.setUserToBlockedUser(request));

        return ResponseEntity.status(HttpStatus.CREATED)
            .header("Content-Type", "application/json")
            .body(response);
    }

    @DeleteMapping("/api/admin/user/unblocked")
    public ResponseEntity<UserManagementResponse> setBlockUserToUnBlock(@RequestBody UserRequest request) {
        UserManagementResponse response = new UserManagementResponse(userManagementService.setBlockedUserToUnblock(request));

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(response);
    }
}
