package com.springbootcommunitydevproj.controller;

import com.springbootcommunitydevproj.dto.PostRequest;
import com.springbootcommunitydevproj.dto.PostResponse;
import com.springbootcommunitydevproj.model.User;
import com.springbootcommunitydevproj.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@Tag(name = "게시글 CRUD 컨트롤러")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/api/post/{boardName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // 게시글 생성 요청
    @Operation(summary = "게시글 등록", description = "특정 게시판에 게시글을 등록할 때 사용하는 API입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "게시글 등록 성공", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 게시판에 게시글 작성 시도", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> savePost(
        @Parameter(name = "boardName")
        @PathVariable(name = "boardName") String boardName,
        @ModelAttribute PostRequest newPost,
        @AuthenticationPrincipal User user) {

        try {
            postService.savePost(boardName, newPost, user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 게시판에 게시글 작성을 시도하였습니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 성공적으로 작성되었습니다.");
    }

    @GetMapping("/api/post/{post_id}") // 게시글 조회
    @Operation(summary = "게시글 조회", description = "게시글을 조회할 때 사용하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "게시물 조회 성공", content = @Content(mediaType = "application/json"))
    public ResponseEntity<PostResponse> showPost(
        @Parameter(name = "post_id") @PathVariable(name = "post_id") Integer id,
        @AuthenticationPrincipal User user) {

        try {
            return ResponseEntity.ok(postService.findById(id, user.getId(), "false").toResponse());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getReason());
        }
    }

    @PutMapping(value = "/api/post/{post_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // 게시글 수정
    @Operation(summary = "게시글 수정", description = "게시글을 수정할 때 사용하는 API입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "게시글 수정 사항이 없음", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "게시글 수정 도중 문제 발생", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> updatePost(
        @Parameter(name = "post_id") @PathVariable(name = "post_id") Integer post_id
        , @ModelAttribute PostRequest updatePost) {
        String result = "";

        try {
            result = postService.update(post_id, updatePost);
        } catch (Exception e) {
            if (e.getMessage().equals("변경 사항이 없습니다.")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/api/post/{post_id}") // 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제할 때 사용하는 API입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글에 삭제 시도")
    })
    public ResponseEntity<Void> deletePost(
        @Parameter(name = "post_id") @PathVariable(name = "post_id") Integer post_id
    ) {

        try {
            postService.delete(post_id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}