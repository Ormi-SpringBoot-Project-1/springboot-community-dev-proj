package com.springbootcommunitydevproj.model;

import com.springbootcommunitydevproj.dto.PostResponse;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Modifying;

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
    @Column(name = "post_id", updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "views")
    @ColumnDefault("0")
    private Integer views;

    @Column(name = "post_file_count", columnDefinition = "TINYINT")
    @ColumnDefault("0")
    private Integer postFileCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id")
    private PostAuthority authority;

    public PostResponse toResponse() {
        return PostResponse.builder()
            .postId(id)
            .authorId(user == null ? null : user.getId())
            .authorNickname(user == null ? null : user.getNickname())
            .authorLevelName(user == null ? null : user.getLevel().getLevelName())
            .title(title)
            .content(content)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .views(views)
            .build();
    }

    public Post updateViews() {
        views++;
        return this;
    }

}
