package com.springbootcommunitydevproj.dto;

import lombok.Builder;

@Builder
public class PostResponse {
    private String title;
    private String content;
}
