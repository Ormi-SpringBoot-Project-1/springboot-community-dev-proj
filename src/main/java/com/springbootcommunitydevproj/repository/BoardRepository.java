package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
