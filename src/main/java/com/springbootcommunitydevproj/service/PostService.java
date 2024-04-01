package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.dto.UpdatePostRequest;
import com.springbootcommunitydevproj.model.*;
import com.springbootcommunitydevproj.repository.BoardAuthorityRepository;
import com.springbootcommunitydevproj.repository.BoardRepository;
import com.springbootcommunitydevproj.repository.PostAuthorityRepository;
import com.springbootcommunitydevproj.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.springbootcommunitydevproj.utils.ResponseMessages.POST_ID_NOT_FOUND;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final BoardAuthorityRepository boardAuthorityRepository;
    private final PostAuthorityRepository postAuthorityRepository;
    public PostService(PostRepository postRepository, BoardRepository boardRepository,
                       BoardAuthorityRepository boardAuthorityRepository, PostAuthorityRepository postAuthorityRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.boardAuthorityRepository = boardAuthorityRepository;
        this.postAuthorityRepository = postAuthorityRepository;
    }

    // 게시글 저장 api
    public Post savePost(Integer board_id, Integer auth_id, AddPostRequest request) {

        // 게시글 구성 요소: 제목, 컨텐츠, 유저, 좋아요, 싫어요, 조회수, 글 권한
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .board(boardRepository.findById(board_id).get()) // 게시판
                .user(request.getUser())
                .likes(0) // 디폴트 0
                .dislikes(0) // 디폴트 0
                .views(0) // 디폴트 0
                .authority(postAuthorityRepository.findById(auth_id).get()) // 게시글 권한
                .build();

        return postRepository.save(post);
    }

    // 상세 게시글 불러오기 api
    public Post findById(Integer id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(POST_ID_NOT_FOUND));
    }

    // 게시글 수정 api
    @Transactional
    public Post update(Integer id, UpdatePostRequest request) {

        Post post = findById(id);

        String title = post.getTitle();
        String content = post.getContent();

        if (title != request.getTitle()) {
            title = request.getTitle();
        }

        if (content != request.getContent()) {
            content = request.getContent();
        }

        post.update(title, content);
        return post;
    }

    // 게시글 삭제 api
    public void delete(Integer id) {

        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            // 게시글 삭제
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException(POST_ID_NOT_FOUND);
        }
    }
}
