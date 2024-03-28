package com.springbootcommunitydevproj.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddUserRequest {
    private Integer Id;
    private String nickname;
    private String email;
    private String password;
    private String description;
    private Integer levelId;
    private Integer reportedCount;
    private LocalDateTime createdAt;
    private Boolean isAdmin;
    private String userIp;
    private String lastLogInIp;
    private String phoneNumber;
}