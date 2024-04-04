package com.springbootcommunitydevproj.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blocked_user")
public class BlockedUser {

    @Id
    @Column(name = "user_id", updatable = false)
    private Integer userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_ip", nullable = false)
    private String userIp;
}
