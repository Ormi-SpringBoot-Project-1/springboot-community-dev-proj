package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.service.UserManagementService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
