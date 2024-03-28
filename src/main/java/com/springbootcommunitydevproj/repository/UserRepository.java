package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
