package com.springbootcommunitydevproj.dto;

import com.springbootcommunitydevproj.model.Board;
import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.model.PostAuthority;
import com.springbootcommunitydevproj.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Board board;
    private User user;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer views;
    private Integer likes;
    private Integer dislikes;
    private PostAuthority authority;

    public PostResponse(Post post) {
        board = post.getBoard();
        user = post.getUser();
        title = post.getTitle();
        content = post.getContent();;
        createdAt = post.getCreatedAt();
        updatedAt = post.getUpdatedAt();
        views = post.getViews();
        likes = post.getLikes();
        dislikes = post.getDislikes();
        authority = post.getAuthority();
    }

    public void updateViews() {
        views++;
    }
}
