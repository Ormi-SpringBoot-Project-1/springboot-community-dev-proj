package com.springbootcommunitydevproj.dto;

import com.springbootcommunitydevproj.model.Board;
import com.springbootcommunitydevproj.model.PostAuthority;
import com.springbootcommunitydevproj.model.PostLikes;
import com.springbootcommunitydevproj.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPostRequest {

    // 일단 테스트에 필요한 컬럼들만 작성함. 더 추가 예정.
    private String title;
    private String content;
    private PostAuthority authority;
    private Board board;
    private User user;
    private Integer postFileCount;
}
