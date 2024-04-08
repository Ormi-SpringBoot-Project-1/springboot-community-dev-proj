package com.springbootcommunitydevproj.dto;

import com.springbootcommunitydevproj.model.Level;
import com.springbootcommunitydevproj.model.User;
import lombok.*;

import java.time.LocalDateTime;

/**
 *      회원 관련 Response를 위한 DTO 입니다.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Integer Id;
    private String nickname;
    private String email;
    private String description;
    private Level level;
    private Integer reportedCount;
    private LocalDateTime createdAt;
    private Boolean isAdmin;
    private String userIp;
    private String lastLogInIp;
    private String phoneNumber;

    public UserResponse(User user) {
        Id = user.getId();
        nickname = user.getNickname();
        email = user.getEmail();
        description = user.getDescription();
        level = user.getLevel();
        reportedCount = user.getReportedCount();
        isAdmin = user.getIsAdmin();
        phoneNumber = user.getPhoneNumber();
    }
}