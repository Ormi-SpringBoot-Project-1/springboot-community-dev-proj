package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PostViewController { // 전체 게시판, 특정 게시판 화면 목록

    private final BoardService boardService;
    public PostViewController(BoardService boardService) {
        this.boardService = boardService;
    }
    // 전체 글 목록
    @GetMapping("/posts")
    public String showAllPosts(Model model) {

        return "PostList";
    }

    // 특정 게시판 글 목록
    @GetMapping("/posts/{board_id}")
    public String showBoardPosts(@PathVariable(name="board_id") Integer board_id, Model model) {

        List<Post> posts = boardService.getBoardPost(board_id);
        model.addAttribute("posts", posts);

        return "PostBoard";
    }
}
