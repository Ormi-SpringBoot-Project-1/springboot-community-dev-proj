package com.springbootcommunitydevproj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostViewController {

    // 전체 글 목록
    @GetMapping("/posts")
    public String showAllPosts(Model model) {

        return "View";
    }

    // 특정 게시판 글 목록
    @GetMapping("/posts/{board_id}")
    public String showBoardPosts(@PathVariable(name="board_id") Integer board_id, Model model) {

        return "ViewBoard";
    }
}
