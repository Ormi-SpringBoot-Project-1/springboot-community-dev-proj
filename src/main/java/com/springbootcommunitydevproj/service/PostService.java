package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.dto.UpdatePostRequest;
import com.springbootcommunitydevproj.model.*;
import com.springbootcommunitydevproj.repository.BoardAuthorityRepository;
import com.springbootcommunitydevproj.repository.BoardRepository;
import com.springbootcommunitydevproj.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final BoardAuthorityRepository boardAuthorityRepository;
    public PostService(PostRepository postRepository, BoardRepository boardRepository,
                       BoardAuthorityRepository boardAuthorityRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.boardAuthorityRepository = boardAuthorityRepository;
    }

    public Post savePost(Integer board_id, AddPostRequest request) {

        // 유저 정보는 어떻게 넘겨주지....?
        // 게시판 정보는 url을 통해서 넘겨준다..
        // boardAuthority는?
        // 게시판 권한은 설정칸을 만들어서 url로 넘겨준다.

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                //.board(request.getBoard())
                .board(boardRepository.findById(board_id).get())
                .user(request.getUser())
                .likes(0)
                .dislikes(0)
                .views(0)
                .postFileCount(request.getPostFileCount())
                .authority(request.getAuthority())
                .build();

        return postRepository.save(post);
    }

    public Post findById(Integer id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다: " + id));
    }

    @Transactional
    public Post update(Integer id, UpdatePostRequest request) {

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다: " + id));

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
            throw new IllegalArgumentException("게시글이 존재하지 않습니다: " + id);
        }
    }
}
