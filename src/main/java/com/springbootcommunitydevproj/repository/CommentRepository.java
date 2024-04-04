package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByBoardIdOrderByCreatedDateDesc(String BoardId);
    List<Comment> findAllByIdOrderByCreatedDateDesc(String id);
}