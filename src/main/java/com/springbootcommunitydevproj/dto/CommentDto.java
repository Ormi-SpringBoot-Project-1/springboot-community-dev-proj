package com.springbootcommunitydevproj.dto;

import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.model.Comment;

import java.time.LocalDateTime;

public class CommentDto {
    private Integer comment_id;
    private Post post;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentDto toCommentDto(Comment comment, Long postId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCommentWriter(comment.getCommentWriter());
        commentDto.setCommentContents(comment.getCommentContents());
        commentDto.setCommentCreatedTime(comment.getCreatedTime());
        commentDto.setBoardId(postId);
        return commentDto;
}

    private void setId(Integer id) {
    }
