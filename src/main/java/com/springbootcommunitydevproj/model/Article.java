package com.springbootcommunitydevproj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_id", updatable = false)
    private Integer article_id;

    @Column(name="board_id", nullable = false)
    private Integer board_id;

    @Column(name="user_id", nullable = false)
    private Integer user_id;

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

    @Column(name="limit_access_level", nullable = false)
    @ColumnDefault("5")
    private Byte limit_access_level;

    @Column(name="views", nullable = false)
    @ColumnDefault("0")
    private Integer views;

    @Column(name="likes", nullable = false)
    @ColumnDefault("0")
    private Integer likes;

    @Column(name="dislikes", nullable = false)
    @ColumnDefault("0")
    private Integer dislikes;

    @Column(name="article_order", nullable = false)
    @ColumnDefault("0")
    private Integer article_order;

    @Column(name="comment_count", nullable = false)
    @ColumnDefault("0")
    private Integer comment_count;

    @Column(name="post_file_count", nullable = false)
    @ColumnDefault("0")
    private Integer post_file_count;
}
