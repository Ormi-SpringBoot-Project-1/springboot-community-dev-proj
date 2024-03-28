package com.springbootcommunitydevproj.dto;

import com.springbootcommunitydevproj.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Integer comment_id;
    private Post post;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentDTO toCommentDTO(Comment comment, Long postId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setCommentWriter(comment.getCommentWriter());
        commentDTO.setCommentContents(comment.getCommentContents());
        commentDTO.setCommentCreatedTime(comment.getCreatedTime());
        commentDTO.setBoardId(postId);
        return commentDTO;
    }
}