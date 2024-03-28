package com.springbootcommunitydevproj.dto;

import com.springbootcommunitydevproj.model.Board;
import com.springbootcommunitydevproj.model.PostAuthority;
import com.springbootcommunitydevproj.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddPostRequest {
    private String title;
    private String content;
    private PostAuthority authority;
    private Board board;
    private User user;
}
