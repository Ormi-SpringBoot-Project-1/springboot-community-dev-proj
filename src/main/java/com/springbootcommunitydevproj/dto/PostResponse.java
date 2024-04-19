package com.springbootcommunitydevproj.dto;

import com.springbootcommunitydevproj.model.Post;
import lombok.*;

import java.time.LocalDateTime;

/**
 *      게시글 정보를 반환하기 위한 DTO 입니다.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Integer postId;
    private Integer authorId;
    private String authorNickname;
    private String authorLevelName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer views;

    public PostResponse(Post post) {
        postId = post.getId();
        authorId = post.getUser().getId();
        authorNickname = post.getUser().getNickname();
        authorLevelName = post.getUser().getLevel().getLevelName();
        title = post.getTitle();
        content = post.getContent();
        createdAt = post.getCreatedAt();
        updatedAt = post.getUpdatedAt();
        views = post.getViews();
    }
}
