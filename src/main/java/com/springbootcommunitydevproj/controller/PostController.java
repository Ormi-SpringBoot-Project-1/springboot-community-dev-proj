package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.dto.PostResponse;
import com.springbootcommunitydevproj.dto.UpdatePostRequest;
import com.springbootcommunitydevproj.model.Post;
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
    public ResponseEntity<PostResponse> savePost(@RequestParam Integer board_id, @RequestBody AddPostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.savePost(board_id, request).toResponse());
    }

    @GetMapping("/api/post/{post_id}") // 게시글 조회
    public ResponseEntity<PostResponse> showPost(@PathVariable(name="post_id") Integer post_id) {
        return ResponseEntity.ok(postService.findById(post_id).toResponse());
    }

    @PutMapping("/api/post/{post_id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Integer post_id, @RequestBody UpdatePostRequest request) {

        try {
            Post post = postService.update(post_id, request);
            return ResponseEntity.ok(post.toResponse());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/api/post/{post_id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer post_id) {

        try {
            postService.delete(post_id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}