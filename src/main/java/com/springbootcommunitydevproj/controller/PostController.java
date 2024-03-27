package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.dto.PostResponse;
import com.springbootcommunitydevproj.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/articles") // 게시글 생성 요청
    @ResponseBody
    public ResponseEntity<PostResponse> saveArticle(@RequestBody AddPostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.saveArticle(request).toResponse());
    }
}