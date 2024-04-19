package com.springbootcommunitydevproj.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *      등급 테이블 Entity
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`level`")
public class Level {

    @Id
    @Column(name = "level_id", updatable = false)
    private Integer levelId;

    @Column(name = "level", columnDefinition = "TINYINT", nullable = false)
    private Integer level;

    @Column(name = "level_name", nullable = false)
    private String levelName;
}
