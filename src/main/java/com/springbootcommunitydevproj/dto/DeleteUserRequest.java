package com.springbootcommunitydevproj.dto;

import lombok.Data;

@Data
public class DeleteUserRequest {
    private String password;
    private String confirmPassword;
}