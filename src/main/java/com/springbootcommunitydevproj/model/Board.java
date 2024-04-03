package com.springbootcommunitydevproj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
@DynamicInsert
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id", updatable = false)
    private Integer id;

    @Column(name = "board_name")
    @ColumnDefault("")
    private String boardName;

    @Column(name="board_type")
    @ColumnDefault("1")
    private Byte board_type;

    @Column(name="page_post_count")
    @ColumnDefault("10")
    private Integer pagePostCount;

    @Column(name="post_order")
    @ColumnDefault("0")
    private Integer postOrder;

    @Column(name="description")
    @ColumnDefault("")
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="auth_id")
    private BoardAuthority authority;
}
