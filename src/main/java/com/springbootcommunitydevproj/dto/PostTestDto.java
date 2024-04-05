package com.springbootcommunitydevproj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *      테스트용 DTO입니다.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostTestDto implements PostListDto {

    private Integer postId;
    private String title;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private Integer views;
    private String author;
    private Integer comments;
    private Integer accessLevel;
}
