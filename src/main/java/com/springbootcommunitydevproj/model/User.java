package com.springbootcommunitydevproj.model;

import com.springbootcommunitydevproj.dto.UserResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", updatable = false)
    private Integer id;

    @Column(name = "nickname", length = 50, nullable = false, unique = true)
    private String nickname;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Setter
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "level_id", nullable = false)
    private Integer levelId;

    @Column(name = "reported_count", nullable = false)
    @ColumnDefault("0")
    private Integer reportedCount;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name="is_admin", nullable = false)
    @ColumnDefault("0")
    private Boolean isAdmin;

    @Column(name="user_ip", length = 50, nullable = false)
    private String userIp;

    @Column(name="last_login_ip", length = 50, nullable = false)
    private String lastLogInIp;

    @Column(name="phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Builder
    public User(Integer id, String nickname, String email, String password, String description, Integer levelId, Integer reportedCount, LocalDateTime createdAt, Boolean isAdmin, String userIp, String lastLogInIp, String phoneNumber) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.description = description;
        this.levelId = levelId;
        this.reportedCount = reportedCount;
        this.createdAt = createdAt;
        this.isAdmin = isAdmin;
        this.userIp = userIp;
        this.lastLogInIp = lastLogInIp;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 계정 잠금 여부 반환 (true: 잠금 안됨)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 만료 여부 반환 (true: 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 패스워드의 만료 여부 반환 (true: 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        // 사용자의 비밀번호를 일정 기간 지나서 변경하는 정책이 있을 경우,
        // 비밀번호가 만료되었다면 사용자의 로그인을 거부할 수 있음.
        return true;
    }

    // 계정 사용 여부 반환 (true: 사용 가능)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserResponse toResponse() {
        return UserResponse.builder()
                .Id(id)
                .nickname(nickname)
                .email(email)
                .description(description)
                .levelId(levelId)
                .reportedCount(reportedCount)
                .isAdmin(isAdmin)
                .phoneNumber(phoneNumber)
                .build();
    }
}