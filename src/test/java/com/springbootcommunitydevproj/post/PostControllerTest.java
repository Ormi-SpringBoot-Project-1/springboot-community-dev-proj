package com.springbootcommunitydevproj.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

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

    @BeforeEach
    void setUpMock() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getPostById() throws Exception {
        //given

        Optional<Post> post = postRepository.findById(1);
        Integer id = post.get().getId();

        //when
        ResultActions response = mockMvc.perform(get("/api/post/{post_id}", id));

        response.andExpect(status().is2xxSuccessful());

        // Optional<Post> postOptional = postRepository.findById(id);

        response.andExpect(jsonPath("$.title").value(post.get().getTitle()))
                .andExpect(jsonPath("$.content").value(post.get().getContent()));
    }

    @Test
    void savePost() throws Exception {
        AddPostRequest request = AddPostRequest.builder()
                .title("title")
                .content("content")

                .build();
        String json = objectMapper.writeValueAsString(request);

        ResultActions response = mockMvc.perform(post("/api/post").content(json).contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("content").value(request.getContent()));
    }
}
