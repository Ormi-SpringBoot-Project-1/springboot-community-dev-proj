package com.springbootcommunitydevproj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    // 일단 테스트에 필요한 컬럼들만 작성함. 더 추가 예정.
    private String title;
    private String content;
    private Integer accessLevel;
    private Integer commentLevel;
    private Integer postFileCount;
}
