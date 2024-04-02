package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.PostRequest;
import com.springbootcommunitydevproj.dto.PostResponse;
import com.springbootcommunitydevproj.dto.UpdatePostRequest;
import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/post/{boardName}") // 게시글 생성 요청
    public ResponseEntity<String> savePost(
        @PathVariable(name = "boardName") String boardName,
        @ModelAttribute PostRequest newPost,
        @AuthenticationPrincipal User user) {

        try {
            postService.savePost(boardName, newPost, user);
        } catch (IllegalArgumentException e) {
            log.error("Board Name 불일치");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시판에 게시글 작성을 시도하였습니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 성공적으로 작성되었습니다.");
    }

    @GetMapping("/api/post/{post_id}") // 게시글 조회
    public ResponseEntity<PostResponse> showPost(@PathVariable(name = "post_id") Integer id) {
        postService.findById(id);
        return ResponseEntity.ok(postService.findById(id).toResponse());
    }

    @PutMapping("/api/post/{post_id}") // 게시글 수정
    public ResponseEntity<String> updatePost(@PathVariable Integer post_id, @RequestBody UpdatePostRequest request) {

        try {
            String result = postService.update(post_id, request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/api/post/{post_id}") // 게시글 삭제
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