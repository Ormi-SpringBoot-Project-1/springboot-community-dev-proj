package com.springbootcommunitydevproj.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="like_or_dislike_id", updatable = false)
    private Integer id;

    @Column(name="like_or_dislike", columnDefinition = "TINYINT", nullable = false)
    @ColumnDefault("3") // 1: 좋아요 / 2: 싫어요 / 3: 안누름
    private Byte likeOrDislike;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
