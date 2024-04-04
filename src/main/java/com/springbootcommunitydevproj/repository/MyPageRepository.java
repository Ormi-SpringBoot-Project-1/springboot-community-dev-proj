package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyPageRepository extends JpaRepository<Board, String> {
    List<Board> findAllByIdOrderByCreatedDateDesc(String id);
    List<Board> findAllByBoardIdInOrderByCreatedDateDesc(List<String> boardIds);
}