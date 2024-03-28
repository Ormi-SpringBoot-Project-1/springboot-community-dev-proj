package com.springbootcommunitydevproj.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Integer userId;
    private String nickname;
    private String email;
    private String password;
    private String description;
    private String levelId;
    private Integer level;
    private Integer reportedCount;
    private LocalDateTime createdAt;
    private Integer isAdmin;
    private String userIp;
    private String lastLoginIp;
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
