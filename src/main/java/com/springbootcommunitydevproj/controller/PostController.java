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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Tag(name = "게시글 CRUD 컨트롤러")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     *      boardName 게시판에 회원이 작성한 게시글을 DB로 저장하고 그 결과를 메시지로 반환합니다. <br>
     *      boardName으로 게시글을 등록할 게시판을 특정합니다. <br>
     *      회원이 작성한 게시글 폼 정보는 Model Attribute인 PostRequest 객체에 매핑됩니다. <br>
     *      게시글을 작성한 회원 정보는 Spring Security의 AuthenticationPrincipal을 통해 userDetails를 얻습니다. <br>
     *      게시글이 성공적으로 등록되면 201 Code와 함께 게시글 등록 성공 메시지를, 실패하면 404 Code와 함께 게시글 등록 실패 메시지를 반환합니다.
     */
    @PostMapping(value = "/api/post/{boardName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    /**
     *      게시글 조회를 위해 post id를 통해 특정 게시물의 세부 정보를 반환합니다. <br>
     *      게시글을 성공적으로 DB에서 찾았다면 그 정보를 반환하고 없다면 404 Code와 함께 없는 게시물임을 알리는 메시지를 반환합니다. <br>
     *      만약 조회하고자 하는 게시글의 공개 등급이 현재 회원의 등급보다 높다면 403 Code와 함께 접근 제한 메시지를 반환합니다.
     */
    @GetMapping("/api/post/{post_id}")
    @Operation(summary = "게시글 조회", description = "게시글을 조회할 때 사용하는 API입니다.")
    @ApiResponses(value = {
        @ApiResponse (responseCode = "200", description = "게시물 조회 성공", content = @Content(mediaType = "application/json")),
        @ApiResponse (responseCode = "403", description = "게시물 접근 제한", content = @Content(mediaType = "application/json")),
        @ApiResponse (responseCode = "404", description = "존재하지 않는 게시글", content = @Content(mediaType = "application/json"))
    })
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

    /**
     *      post id를 통해 특정 게시글을 수정하고 그 결과 메시지를 반환합니다. <br>
     *      현재 게시글의 정보는 Model Attribute인 PostRequest 객체에 매핑됩니다. <br>
     *      게시글 수정이 정상적으로 이루어 졌다면 게시글 수정 성공 메시지를 반환합니다. <br>
     *      만약 게시글 수정 사항이 없다면 404 Code와 함께 수정 사항이 없다는 메시지를 반환하고 수정 도중 내부 문제가 발생했다면 500 Code와 함께 문제 발생 메시지를 반환합니다.
     */
    @PutMapping(value = "/api/post/{post_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "게시글 수정", description = "게시글을 수정할 때 사용하는 API입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "게시글 수정 사항이 없음", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "게시글 수정 도중 문제 발생", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> updatePost(
        @Parameter(name = "post_id") @PathVariable(name = "post_id") Integer post_id,
        @ModelAttribute PostRequest updatePost) {
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

    /**
     *      post id를 통해 게시글을 삭제하고 그 결과 코드를 반환합니다. <br>
     *      게시글 삭제에 성공하면 200 Code를, 실패하면 404 Code를 반환합니다.
     */
    @DeleteMapping("/api/post/{post_id}")
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