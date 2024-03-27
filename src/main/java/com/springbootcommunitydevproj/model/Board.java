package com.springbootcommunitydevproj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id", updatable = false)
    private Integer id;

    @Column(name="board_type", nullable = false)
    private Integer board_type;

    @Column(name="page_post_count", nullable = false)
    private Integer pagePostCount;

    @Column(name="post_order", nullable = false)
    private Integer postOrder;

    @Column(name="description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name="auth_id")
    private BoardAuthority authority;
}
