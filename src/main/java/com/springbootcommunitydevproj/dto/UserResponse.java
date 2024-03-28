package com.springbootcommunitydevproj.dto;

import com.springbootcommunitydevproj.entity.User;
import lombok.*;

import java.time.LocalDateTime;

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
    private Integer levelId;
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
        levelId = user.getLevelId();
        reportedCount = user.getReportedCount();
        createdAt = user.getCreatedAt();
        isAdmin = user.getIsAdmin();
        userIp = user.getUserIp();
        lastLogInIp = user.getLastLogInIp();
        phoneNumber = user.getPhoneNumber();
    }
}