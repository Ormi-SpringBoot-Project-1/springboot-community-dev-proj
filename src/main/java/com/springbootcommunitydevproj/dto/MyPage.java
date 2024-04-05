package com.springbootcommunitydevproj.dto;

import com.springbootcommunitydevproj.model.Level;
import com.springbootcommunitydevproj.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPage {
    private String nickname;
    private String email;
    private String description;
    private Level level;
    private String phoneNumber;

    public MyPage(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.description = user.getDescription();
        this.level = user.getLevel();
        this.phoneNumber = user.getPhoneNumber();
    }
}
