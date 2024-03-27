package com.springbootcommunitydevproj.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BoardAuthority {
    @Id
    @Column(name = "auth_id", nullable = false)
    private Integer id ;

    @Column(name = "auth_create_post_level", nullable = false)
    @ColumnDefault("5")
    private Byte authCreatePostLevel;

    @Column(name = "auth_access_board_level", nullable = false)
    @ColumnDefault("5")
    private Byte authAccessBoardLevel;

    @Column(name = "auth_comment_level", nullable = false)
    @ColumnDefault("5")
    private Byte authCommentLevel;
}
