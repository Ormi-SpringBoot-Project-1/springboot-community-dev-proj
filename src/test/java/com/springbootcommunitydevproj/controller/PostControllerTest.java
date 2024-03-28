package com.springbootcommunitydevproj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.model.*;
import com.springbootcommunitydevproj.repository.PostRepository;
import com.springbootcommunitydevproj.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext ac;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    void savePost() throws Exception {

        BoardAuthority boardAuthority = BoardAuthority.builder()
                .authAccessBoardLevel((byte) 2)
                .authCommentLevel((byte) 2)
                .authCreatePostLevel((byte) 2)
                .build();

        Board board = Board.builder()
                .board_type(1)
                .authority(boardAuthority)
                .postOrder(0)
                .pagePostCount(0)
                .build();

        User user = User.builder()
                .nickname("test")
                .email("test@test.com")
                .password("1234")
                .build();

        PostAuthority postAuthority = PostAuthority.builder()
                .authAccessBoardLevel(1)
                .authCommentLevel(1)
                .build();

        AddPostRequest request = AddPostRequest.builder()
                .title("New Title")
                .content("New Content")
                .board(board)
                .user(user)
                .authority(postAuthority)
                .build();

        Post post = postService.savePost(request);

        Assertions.assertEquals("New Title", post.getTitle());
        Assertions.assertEquals("New Content", post.getContent());

    }

    @Test
    void showPost() {
    }
}