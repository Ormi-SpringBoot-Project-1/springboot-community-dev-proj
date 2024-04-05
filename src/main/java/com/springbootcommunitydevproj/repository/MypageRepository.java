//package com.springbootcommunitydevproj.repository;
//
//import com.springbootcommunitydevproj.model.Board;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface MypageRepository extends JpaRepository<Board, String> {
//    List<Board> findAllByIdOrderByCreatedAtDesc(Integer id);
//    List<Board> findAllByBoardIdInOrderByCreatedDateDesc(List<String> boardIds);
//}
