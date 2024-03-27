package com.springbootcommunitydevproj.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", updatable = false)
    private Integer id;

    @Column(name = "nickname" , nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "description")
    private String description;

    @Column(name = "level_id" , nullable = false)
    private Integer levelId;

    @Column(name = "reported_count" , nullable = false)
    @ColumnDefault("0")
    private Integer reportedCount;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name="is_admin", nullable = false)
    @ColumnDefault("0")
    private Boolean isAdmin;

    @Column(name="user_ip", nullable = false)
    private String userIp;

    @Column(name="last_login_ip", nullable = false)
    private String lastLogInIp;

    @Column(name="phone_number", nullable = false)
    private String phoneNumber;
}