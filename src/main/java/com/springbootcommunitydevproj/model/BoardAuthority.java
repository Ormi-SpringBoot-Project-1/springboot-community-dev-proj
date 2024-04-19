package com.springbootcommunitydevproj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *      게시판 접근 권한 테이블 Entity
 */

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class BoardAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id", updatable = false)
    private Integer id ;

    @Column(name = "auth_create_post_level", columnDefinition = "TINYINT", nullable = false)
    @ColumnDefault("5")
    private Byte authCreatePostLevel;

    @Column(name = "auth_access_board_level", columnDefinition = "TINYINT", nullable = false)
    @ColumnDefault("5")
    private Byte authAccessBoardLevel;

    @Column(name = "auth_comment_level", columnDefinition = "TINYINT", nullable = false)
    @ColumnDefault("5")
    private Byte authCommentLevel;
}
