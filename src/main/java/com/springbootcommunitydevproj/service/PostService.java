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

    public Post savePost(Integer board_id, Integer auth_id, AddPostRequest request) {

        // 유저 정보는 어떻게 넘겨주지....?

        // 게시판 정보는 url을 통해서 넘겨준다..
        // 게시판 권한은 설정칸을 만들어서 url로 넘겨준다.

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .board(boardRepository.findById(board_id).get())
                .user(request.getUser())
                .likes(0)
                .dislikes(0)
                .views(0)
                .authority(postAuthorityRepository.findById(auth_id).get())
                .build();

        return postRepository.save(post);
    }

    public Post findById(Integer id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(POST_ID_NOT_FOUND));
    }

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
