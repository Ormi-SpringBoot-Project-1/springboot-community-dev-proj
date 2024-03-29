package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.dto.PostResponse;
import com.springbootcommunitydevproj.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/post") // 게시글 생성 요청
    public ResponseEntity<PostResponse> savePost(@RequestBody AddPostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.savePost(request).toResponse());
    }

    @GetMapping("/api/post/{post_id}") // 게시글 조회
    public ResponseEntity<PostResponse> showPost(@PathVariable Integer post_id) {
        postService.findById(post_id).toResponse().updateViews();
        return ResponseEntity.ok(postService.findById(post_id).toResponse());
    }


}