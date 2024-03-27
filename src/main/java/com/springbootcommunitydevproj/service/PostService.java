package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.AddPostRequest;
import com.springbootcommunitydevproj.model.Post;
import com.springbootcommunitydevproj.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(AddPostRequest request) {

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent()).build();

        return postRepository.save(post);
    }

    public Post findById(Integer id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found id" + id));
    }
}
