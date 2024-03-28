package com.springbootcommunitydevproj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.model.*;
import com.springbootcommunitydevproj.repository.*;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardAuthorityRepository boardAuthorityRepository;

    @Autowired
    private PostAuthorityRepository postAuthorityRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void savePost() throws Exception {

        // 일단 몇개의 컬럼들은 제외한 상태로 테스트 해봄.
        //

        // Post 엔티티와 연관관계인 엔티티들을 가져옴.
        // 엔티티 주석 풀어서, 현재 테스트 코드 돌리면 not null property 에러 발생합니다.
        BoardAuthority boardAuthority = boardAuthorityRepository.getById(1);
        Board board = boardRepository.getById(1);
        User user = userRepository.getById(1);
        PostAuthority postAuthority = postAuthorityRepository.getById(1);

        // 빌더로 작성한 후에,
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