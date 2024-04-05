package com.springbootcommunitydevproj.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcommunitydevproj.dto.PostRequest;
import com.springbootcommunitydevproj.model.*;
import com.springbootcommunitydevproj.repository.*;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostAuthorityRepository postAuthorityRepository;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUpMock() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(springSecurity())
            .build();
    }

    @Test
    @DisplayName("GET /api/post/{post_id} API 테스트")
    void showPostTest() throws Exception {
        // given
        // 성공 데이터 : 8개 / 실패 데이터 : 5개
        List<Integer> postIdList = List.of(1, 2, 5, 10, 33, 40, 45, 50, 100, 123, -1, -222, 6);
        User user = userRepository.findByEmail("testuser@email.com").get();


        for (int i = 0; i < 13; i++) {
            // when
            ResultActions response = mockMvc.perform(get("/api/post/" + postIdList.get(i))
                .with(SecurityMockMvcRequestPostProcessors.user(user)));

            // then
            if (i < 8) {
                response.andExpect(status().isOk());
            }
            else if (i == 12) {
                response.andExpect(status().isForbidden());
            }
            else {
                response.andExpect(status().isNotFound());
            }
        }
    }

    @Test
    void savePost() throws Exception {

        Board board = boardRepository.getById(1);
        User user = userRepository.getById(1);
        PostAuthority postAuthority = postAuthorityRepository.getById(1);

        PostRequest request = PostRequest.builder()
                .title("title")
                .content("content")
                .build();

        String json = objectMapper.writeValueAsString(request);
        Integer id = board.getId();

        ResultActions response = mockMvc.perform(post("/api/post/{board_id}", id).content(json).contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("content").value(request.getContent()));
    }

    @Test
    void updatePost() {


    }

    @Test
    void deletePost() {


    }
}
