package com.springbootcommunitydevproj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class PostAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id", updatable = false)
    private Integer id;

    @Column(name = "auth_access_board_level", columnDefinition = "TINYINT", nullable = false)
    @ColumnDefault("5")
    private Integer authAccessBoardLevel;

    @Column(name = "auth_comment_level", columnDefinition = "TINYINT", nullable = false)
    @ColumnDefault("5")
    private Integer authCommentLevel;

    public PostAuthority(Integer authAccessBoardLevel, Integer authCommentLevel) {
        this.authAccessBoardLevel = authAccessBoardLevel;
        this.authCommentLevel = authCommentLevel;
    }
}
