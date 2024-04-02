package com.springbootcommunitydevproj.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name = "user")
public class User implements UserDetails {

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

    @Column(name="description")
    private String description;

    @OneToOne()
    @JoinColumn(name="level_id", nullable = false)
    private Level level;

    @Column(name="reported_count")
    @ColumnDefault("0")
    private Integer reportedCount;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="is_admin", columnDefinition = "TINYINT")
    @ColumnDefault("0")
    private Boolean isAdmin;

    @Column(name="user_ip")
    @ColumnDefault("")
    private String userIp;

    @Column(name="last_login_ip")
    @ColumnDefault("")
    private String lastLogInIp;

    @Column(name="phone_num")
    @ColumnDefault("")
    private String phoneNumber;

    @Builder
    public User(String nickname, String email, String password, Level level, Boolean isAdmin) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.level = level;
        this.isAdmin = isAdmin;
    }

    /**
     *      로그인한 사용자의 인증(회원, Admin)과 권한(5 ~ 0등급)을 세팅한다.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> retVal = new ArrayList<>();
        String role = "User";

        if (isAdmin) {
            role = "Admin";
        }

        retVal.add(new SimpleGrantedAuthority(role));

        for (int i = 5; i >= level.getLevel(); i--) {
            retVal.add(new SimpleGrantedAuthority(Integer.toString(i)));
        }

        return retVal;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
