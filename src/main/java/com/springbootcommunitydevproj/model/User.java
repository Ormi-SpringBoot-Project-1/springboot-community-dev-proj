package com.springbootcommunitydevproj.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", updatable = false)
    private Integer id;

    @Column(name="nickname", nullable = false)
    private String nickname;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

//    @Column(name="description")
//    private String description;
//
//    @Column(name="level_id", nullable = false)
//    private Integer levelId;

//    @Column(name="reported_count", nullable = false)
//    @ColumnDefault("0")
//    private Integer reportedCount;
//
//    @CreatedDate
//    @Column(name="created_at")
//    private LocalDateTime createdAt;

//    @Column(name="is_admin", nullable = false)
//    @ColumnDefault("0")
//    private Boolean isAdmin;
//
//    @Column(name="user_ip", nullable = false)
//    private String userIp;
//
//    @Column(name="last_login_ip", nullable = false)
//    private String lastLogInIp;
//
//    @Column(name="phone_number", nullable = false)
//    private String phoneNumber;
}
