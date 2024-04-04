package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.dto.UserManagementResponse;
import com.springbootcommunitydevproj.dto.UserRequest;
import com.springbootcommunitydevproj.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Admin 회원 관리 CRUD 컨트롤러")
@RestController
@AllArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    /**
     *      Admin 페이지의 회원 관리 페이지에 보여질 리스트 목록을 가져옵니다. <br>
     *      Query String의 page 변수를 통해 페이징됩니다. <br>
     *      한 페이지 당 최대 10개의 결과를 가져옵니다. <br>
     *      (3/28 추가) orderby로 어떤 열을 기준으로 정렬할 지, sort로 오름차순 내림차순 정렬할 지 결정합니다. 기본값은 userId, 오름차순 입니다.
     */
    @GetMapping("/api/admin/user_list")
    @Operation(summary = "회원 목록 조회", description = "회원 목록을 조회할 때 사용하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<List<UserManagementInfoDto>> getAllUserManagementInfo(
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "orderby", defaultValue = "userId") String orderBy,
        @RequestParam(name = "sort", defaultValue = "asc") String ascOrDesc) {
        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(userManagementService.getAllUserManagementInfo(page, orderBy, ascOrDesc));
    }

    /**
     *      Admin 페이지의 회원 관리 페이지에서 특정 회원의 닉네임을 검색했을 때 결과를 가져옵니다. <br>
     *      리스트 형태로 Response를 반환합니다.
     */
    @GetMapping("/api/admin/user/{nickname}")
    @Operation(summary = "특정 회원 검색", description = "특정 회원을 검색할 때 사용하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "회원 검색 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<List<UserManagementInfoDto>> getUserManagementInfoByNickname(
        @Parameter(name = "nickname")
        @PathVariable(name = "nickname") String nickname) {
        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(userManagementService.getUserManagementInfoByNickname(nickname));
    }

    /**
     *      Admin 페이지의 특정 회원의 등급을 변경합니다. <br>
     *      변경 결과에 따라 메시지를 Response로 반환합니다.
     */
    @PutMapping("/api/admin/user/level")
    @Operation(summary = "회원 등급 변경", description = "회원 등급을 변경할 때 사용하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "회원 등급 변경 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<UserManagementResponse> changeUserLevel(@RequestBody UserRequest request) {
        UserManagementResponse response = new UserManagementResponse(userManagementService.changeUserLevel(request));

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(response);
    }

    /**
     *      Admin 페이지의 특정 회원의 가입 제한을 설정합니다. <br>
     *      변경 결과에 따라 메시지를 Response로 반환합니다.
     */
    @PostMapping("/api/admin/user/blocked")
    @Operation(summary = "회원 가입 제한 설정", description = "회원의 가입 제한을 설정할 때 사용하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "회원 가입 제한 설정 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<UserManagementResponse> setUserToBlockedUser(@RequestBody UserRequest request) {
        UserManagementResponse response = new UserManagementResponse(
            userManagementService.setUserToBlockedUser(request));

        return ResponseEntity.status(HttpStatus.CREATED)
            .header("Content-Type", "application/json")
            .body(response);
    }

    /**
     *      Admin 페이지의 특정 회원의 가입 제한은 해제합니다.
     */
    @DeleteMapping("/api/admin/user/unblocked")
    @Operation(summary = "회원 가입 제한 해제", description = "회원 가입 제한을 헤제할 때 사용하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "회원 가입 제한 해제 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<UserManagementResponse> setBlockUserToUnBlock(@RequestBody UserRequest request) {
        UserManagementResponse response = new UserManagementResponse(
            userManagementService.setBlockedUserToUnblock(request));

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(response);
    }
}
