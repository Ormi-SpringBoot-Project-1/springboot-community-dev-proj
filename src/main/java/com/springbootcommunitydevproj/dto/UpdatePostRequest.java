package com.springbootcommunitydevproj.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePostRequest {
    private String title;
    private String content;
}
