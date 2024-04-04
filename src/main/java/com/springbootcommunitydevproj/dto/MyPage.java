package com.springbootcommunitydevproj.dto;

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
    private int levelId;
    private String phoneNumber;

    public MyPage(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.description = user.getDescription();
        this.levelId = user.getLevelId();
        this.phoneNumber = user.getPhoneNumber();
    }
}
