package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email); // email 중복 여부 확인

    Boolean existsByNickname(String nickname); // nickname 중복 여부 확인

    Optional<User> findByEmail(String email);  // email로 사용자 정보를 가져옴

    @Query("SELECT COUNT(user) FROM User user")
    Long countAllUsers();
}