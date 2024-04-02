package com.springbootcommunitydevproj.model;

import com.springbootcommunitydevproj.dto.PostResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id", updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id", nullable = true)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name="views", nullable = false)
    @ColumnDefault("0")
    private Integer views;

    @Column(name="likes", nullable = false)
    @ColumnDefault("0")
    private Integer likes;

    @Column(name="dislikes", nullable = false)
    @ColumnDefault("0")
    private Integer dislikes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="auth_id", nullable = false)
    private PostAuthority authority;

    public PostResponse toResponse() {
        return PostResponse.builder()
                .board(board)
                .user(user)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .views(views)
                .authority(authority)
                .build();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateViews() {
        views++;
    }
}
