package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostLikesRepository extends JpaRepository<PostLikes, Integer> {
}
