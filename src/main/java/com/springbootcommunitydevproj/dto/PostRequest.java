package com.springbootcommunitydevproj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *      게시글 작성, 수정 시 폼 데이터와 매핑을 위한 DTO 입니다.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private String title;
    private String content;
    private Integer accessLevel;
    private Integer commentLevel;
    private Integer postFileCount;
}
