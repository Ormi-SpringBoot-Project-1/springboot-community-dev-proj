package com.springbootcommunitydevproj.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *      회원 관련 Request를 매핑하기 위한 DTO 입니다.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Integer userId;
    @NotBlank(message = "nickname is mandatory")
    private String nickname; // 닉네임 (3~12자 이내, 문자/숫자 사용가능)
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "password is mandatory")
    private String password; // 비밀번호(8~16자 이내, 영문/숫자/기호 사용가능)
    private String description;
    private String levelId;
    private Integer level;
    private Integer reportedCount;
    private LocalDateTime createdAt;
    private Integer isAdmin;
    private String userIp;
    private String lastLoginIp;
    @NotBlank(message = "phoneNumber is mandatory")
    private String phoneNum;

    // 테스트용 생성자
    public UserRequest(Integer userId) {
        this.userId = userId;
    }

    // 테스트용 생성자
    public UserRequest(Integer userId, Integer level) {
        this.userId = userId;
        this.level = level;
    }
}
