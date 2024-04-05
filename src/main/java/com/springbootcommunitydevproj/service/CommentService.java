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

    public List<Comment> findCommentsByBoardId(Integer BoardId){
        return commentRepository.findAllByPostOrderByCreatedDesc(BoardId);
    }

    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(Integer.valueOf(commentId));
    }
}