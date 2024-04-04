package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 특정 게시판 글 목록 불러오기
    public List<Post> getBoardPost(Integer id) {
        return boardRepository.getBoardPost(id);
    }
}
