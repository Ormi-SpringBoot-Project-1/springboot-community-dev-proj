package com.springbootcommunitydevproj.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class PostResponse {
    private String title;
    private String content;
}
