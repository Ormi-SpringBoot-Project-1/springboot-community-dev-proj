package com.springbootcommunitydevproj.repository;

import com.springbootcommunitydevproj.model.Board;
import com.springbootcommunitydevproj.model.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 특정 게시판 불러오기 쿼리문
    @Query(value = "select p.title, p.content, u.nickname from post as p outer join users as u on p.user_id = u.id where p.board_id = :id"
            ,nativeQuery = true)
    List<Post> getBoardPost(@Param(value="id") Integer id);

    Optional<Board> findBoardByBoardName(String boardName);
}
