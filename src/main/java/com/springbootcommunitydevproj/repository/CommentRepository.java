package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByIdOrderByCreatedAtDesc(Integer id);

    List<Comment> findAllByPostIdOrderByCreatedAtDesc(Integer postId);
}