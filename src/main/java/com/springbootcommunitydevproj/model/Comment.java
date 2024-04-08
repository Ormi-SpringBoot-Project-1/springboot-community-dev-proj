package com.springbootcommunitydevproj.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 *      댓글 테이블 Entity
 */

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
@DynamicInsert
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id", updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false )
    private Post post;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "is_reported")
    @ColumnDefault("0")
    private Integer is_Reported;

    @Column(name = "likes", nullable = false)
    @ColumnDefault("0")
    private Integer likes;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Comment(User user, Post post, String body, Integer isReported, Integer likes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.post = post;
        this.body = body;
        this.is_Reported = isReported;
        this.likes = likes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}