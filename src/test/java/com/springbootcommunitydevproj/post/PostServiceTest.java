package com.springbootcommunitydevproj.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springbootcommunitydevproj.dto.PostListDto;
import com.springbootcommunitydevproj.dto.PostTestDto;
import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.repository.PostRepository;
import com.springbootcommunitydevproj.service.PostService;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
class PostServiceTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void setUpObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("getPostListByBoardName() 메소드 테스트")
    void getPostListByBoardNameTest() throws Exception {
        // given
        // 자유 게시판, 평가 게시판, 공지 사항 총 30개의 데이터로 테스트
        List<PostTestDto> posts_free = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/test/java/resources/posts_free_orderby_postId_desc_limit_10_offset_0.json"), new TypeReference<List<PostTestDto>>(){});
        List<PostTestDto> posts_evaluation = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/test/java/resources/posts_evaluation_orderby_postId_desc_limit_10_offset_0.json"), new TypeReference<List<PostTestDto>>(){});
        List<PostTestDto> posts_attention = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/test/java/resources/posts_attention_orderby_postId_desc_limit_10_offset_0.json"), new TypeReference<List<PostTestDto>>(){});

        // when
        List<PostListDto> posts_free_result = postService.getPostListByBoardName("자유 게시판", null, 1, "postId", "desc");
        List<PostListDto> posts_evaluation_result = postService.getPostListByBoardName("평가 게시판", null, 1, "postId", "desc");
        List<PostListDto> posts_attention_result = postService.getPostListByBoardName("공지 사항", null, 1, "postId", "desc");

        // then
        for (int i = 0; i < 10; i++) {
            comparePostListDto(posts_free_result.get(i), posts_free.get(i));
            comparePostListDto(posts_evaluation_result.get(i), posts_evaluation.get(i));
            comparePostListDto(posts_attention_result.get(i), posts_attention.get(i));
        }
    }

    @Test
    @DisplayName("getPostListsByUserId() 메소드 테스트")
    void getPostListsByUserIdTest() throws Exception  {
        // given
        // 일반 회원 자유 게시판, Admin 자유 게시판, 일반 회원 평가 게시판, Admin 공지 사항 총 40개의 데이터로 테스트
        // Admin의 Id == 13 / test User의 Id = 14
        List<PostTestDto> admin_posts_free = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/test/java/resources/admin_posts_free_orderby_postId_desc_limit_10_offset_0.json"), new TypeReference<List<PostTestDto>>(){});
        List<PostTestDto> user_posts_free = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/test/java/resources/user_posts_free_orderby_postId_desc_limit_10_offset_0.json"), new TypeReference<List<PostTestDto>>(){});
        List<PostTestDto> admin_posts_attention = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/test/java/resources/admin_posts_attention_orderby_postId_desc_limit_10_offset_0.json"), new TypeReference<List<PostTestDto>>(){});
        List<PostTestDto> user_posts_evaluation = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/test/java/resources/user_posts_evaluation_orderby_postId_desc_limit_10_offset_0.json"), new TypeReference<List<PostTestDto>>(){});

        final Integer adminId = 13;
        final Integer userId = 14;

        // when
        List<PostListDto> admin_posts_free_result = postService.getPostListByUserId("자유 게시판", adminId, 1, "postId", "desc");
        List<PostListDto> user_posts_free_result = postService.getPostListByUserId("자유 게시판", userId, 1, "postId", "desc");
        List<PostListDto> admin_posts_attention_result = postService.getPostListByUserId("공지 사항", adminId, 1, "postId", "desc");
        List<PostListDto> user_posts_evaluation_result = postService.getPostListByUserId("평가 게시판", userId, 1, "postId", "desc");

        // then
        for (int i = 0; i < 10; i++) {
            comparePostListDto(admin_posts_free_result.get(i), admin_posts_free.get(i));
            comparePostListDto(user_posts_free_result.get(i), user_posts_free.get(i));
            comparePostListDto(admin_posts_attention_result.get(i), admin_posts_attention.get(i));
            comparePostListDto(user_posts_evaluation_result.get(i), user_posts_evaluation.get(i));
        }

    }

    @Test
    @DisplayName("getPostPages() 메소드 테스트")
    void getPostPagesTest() {
        // given
        final Integer freeCount = 3;   // 자유 게시판 총 페이지
        final Integer attentionCount = 2;   // 공지 사항 총 페이지
        final Integer recruitCount = 1;   // 그룹 모집 게시판 총 페이지
        final Integer evaluationCount = 2;   // 평가 게시판 총 페이지
        final Integer shareCount = 1;   // 정보 공유 게시판 총 페이지

        // when
        Integer freeCountResult = postService.getPostPages("자유 게시판");
        Integer attentionCountResult = postService.getPostPages("공지 사항");
        Integer recruitCountResult = postService.getPostPages("그룹 모집 게시판");
        Integer evaluationCountResult = postService.getPostPages("평가 게시판");
        Integer shareCountResult = postService.getPostPages("정보 공유 게시판");

        // then
        assertThat(freeCountResult).isEqualTo(freeCount);
        assertThat(attentionCountResult).isEqualTo(attentionCount);
        assertThat(recruitCountResult).isEqualTo(recruitCount);
        assertThat(evaluationCountResult).isEqualTo(evaluationCount);
        assertThat(shareCountResult).isEqualTo(shareCount);
    }

    @Test
    @DisplayName("findById() 메소드 테스트")
    void findByIdTest() throws Exception {
        // given
        // post id가 31 부터 36까지 총 6개의 데이터 + 에러 발생 4개 총 10번의 테스트 진행
        final int start = 31;
        final int end = 36;
        final Integer userId = 13;

        for (int i = start; i <= end ; i++) {
            // given
            Post testData = postRepository.findById(i).get();

            // when
            Post result = postService.findById(i, userId, "true");

            // then
            comparePost(testData, result);
        }

        // when & then
        assertThatThrownBy(() -> {
            postService.findById(12334, userId, "true");
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("게시글이 존재하지 않습니다.");

        assertThatThrownBy(() -> {
            postService.findById(-111, userId, "true");
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("게시글이 존재하지 않습니다.");

        assertThatThrownBy(() -> {
            postService.findById(-12, userId, "true");
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("게시글이 존재하지 않습니다.");

        assertThatThrownBy(() -> {
            postService.findById(9999, userId, "true");
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("게시글이 존재하지 않습니다.");
    }

    /**
     *      PostList 결과와 테스트 데이터를 비교하는 메소드입니다.
     */
    private void comparePostListDto(PostListDto actual, PostListDto expected) {
        assertThat(actual.getPostId()).isEqualTo(expected.getPostId());
        assertThat(actual.getAccessLevel()).isEqualTo(expected.getAccessLevel());
        assertThat(actual.getAuthor()).isEqualTo(expected.getAuthor());
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getComments()).isEqualTo(expected.getComments());
        assertThat(actual.getCreatedAt()).isEqualTo(expected.getCreatedAt());
        assertThat(actual.getUpdatedAt()).isEqualTo(expected.getUpdatedAt());
        assertThat(actual.getViews()).isEqualTo(expected.getViews());
    }

    /**
     *      Post 결과와 테스트 데이터를 비교하는 메소드입니다.
     */
    private void comparePost(Post actual, Post expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getCreatedAt()).isEqualTo(expected.getCreatedAt());
        assertThat(actual.getUpdatedAt()).isEqualTo(expected.getUpdatedAt());
        assertThat(actual.getContent()).isEqualTo(expected.getContent());
        assertThat(actual.getViews()).isEqualTo(expected.getViews());
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getAuthority().getId()).isEqualTo(expected.getAuthority().getId());
        assertThat(actual.getPostFileCount()).isEqualTo(expected.getPostFileCount());
        assertThat(actual.getBoard().getId()).isEqualTo(expected.getBoard().getId());
        assertThat(actual.getUser().getId()).isEqualTo(expected.getUser().getId());
    }
}