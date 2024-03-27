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
    @Column(name="post_id", updatable = false)
    private Integer board_id;

    @Column(name="board_type", nullable = false)
    private Integer board_type;

    @Column(name="page_post_count", nullable = false)
    private Integer page_post_count;

    @Column(name="post_order", nullable = false)
    private Integer post_order;

    @Column(name="description", nullable = false)
    private String description;
}
