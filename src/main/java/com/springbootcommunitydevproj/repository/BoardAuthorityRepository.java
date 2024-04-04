package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.Board;
import com.springbootcommunitydevproj.model.BoardAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardAuthorityRepository extends JpaRepository<BoardAuthority, Integer> {
}
