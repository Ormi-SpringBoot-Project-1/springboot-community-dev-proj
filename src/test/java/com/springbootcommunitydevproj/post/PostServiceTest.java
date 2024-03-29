package com.springbootcommunitydevproj.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.dto.UpdatePostRequest;
import com.springbootcommunitydevproj.model.*;
import com.springbootcommunitydevproj.repository.*;
import com.springbootcommunitydevproj.service.PostService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
class PostServiceTest {

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

    @Autowired
    private PostLikesRepository postLikesRepository;

    @Transactional
    @Test
    void test() throws Exception {

        // 일단 몇개의 컬럼들은 제외한 상태로 테스트 해봄.
        // Post 엔티티와 연관관계인 엔티티들을 가져옴.
        // 엔티티 주석 풀어서, 현재 테스트 코드 돌리면 not null property 에러 발생합니다.
        BoardAuthority boardAuthority = boardAuthorityRepository.getById(1);
        Board board = boardRepository.getById(1);
        User user = userRepository.getById(1);
        PostAuthority postAuthority = postAuthorityRepository.getById(1);

        // 빌더로 리퀘스트 작성
        AddPostRequest request = AddPostRequest.builder()
                .title("New Title")
                .content("New Content")
                .user(user)
                .postFileCount(0)
                .authority(postAuthority)
                .build();

        // Save 로직 테스트
        Post post = postService.savePost(board.getId(), request);
        Assertions.assertEquals("New Title", post.getTitle());
        Assertions.assertEquals("New Content", post.getContent());

        // findById 로직 테스트
        Post getPost = postService.findById(post.getId());
        Assertions.assertNotNull(getPost);

        // 업데이트 로직 테스트
        UpdatePostRequest updateRequest = UpdatePostRequest.builder()
                .title("Update title")
                .content("Update Content")
                .build();

        Post postUpdated = postService.update(post.getId(),updateRequest);
        Assertions.assertEquals(updateRequest.getTitle(), post.getTitle());
        Assertions.assertEquals(updateRequest.getContent(), post.getContent());

        // delete 로직 테스트

    }
}