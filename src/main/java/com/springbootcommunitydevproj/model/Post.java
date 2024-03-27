package com.springbootcommunitydevproj.model;

import com.springbootcommunitydevproj.dto.PostResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id", updatable = false)
    private Integer id;

    @Column(name="board_id", nullable = false)
    private Integer board_id;

    @ManyToOne()
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="auth_id", nullable = false)
    private Integer authId;

    @Column(name="views", nullable = false)
    @ColumnDefault("0")
    private Integer views;

    @Column(name="likes", nullable = false)
    @ColumnDefault("0")
    private Integer likes;

    @Column(name="dislikes", nullable = false)
    @ColumnDefault("0")
    private Integer dislikes;

    @Column(name="post_file_count", nullable = false)
    @ColumnDefault("0")
    private Integer post_file_count;

    public PostResponse toResponse() {
        return PostResponse.builder()
                .title(title)
                .content(title).build();
    }
}
