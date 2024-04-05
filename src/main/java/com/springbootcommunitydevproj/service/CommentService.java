package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.model.Comment;
import com.springbootcommunitydevproj.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public List<Comment> findCommentsByBoardId(Integer postId){
        return commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId);
    }

    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    public void deleteComment(String commentId) {
        commentRepository.deleteById(Integer.valueOf(commentId));
    }
}