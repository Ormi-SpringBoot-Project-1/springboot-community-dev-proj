package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.entity.Post;
import com.springbootcommunitydevproj.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // select * from comment_table where post_id=? order by id desc;
    List<Comment> findAllByPostOrderByIdDesc(Post post);
}