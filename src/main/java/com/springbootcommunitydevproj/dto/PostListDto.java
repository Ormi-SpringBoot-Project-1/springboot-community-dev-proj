package com.springbootcommunitydevproj.dto;

import java.time.LocalDateTime;

/**
 *      게시글 목록을 가져오기 위한 DTO 입니다.
 */
public interface PostListDto {

    Integer getPostId();

    String getTitle();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    Integer getViews();

    String getAuthor();

    Integer getComments();

    Integer getAccessLevel();
}
