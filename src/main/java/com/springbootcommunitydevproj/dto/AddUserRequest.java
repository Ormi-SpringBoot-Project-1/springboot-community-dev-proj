package com.springbootcommunitydevproj.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class AddUserRequest {
    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password; //비밀번호(8~16자 이내, 영문/숫자/기호 사용가능)

    @NotBlank(message = "nickname is mandatory")
    private String nickname; //유저네임 (3~12자 이내, 문자/숫자 사용가능)

    private String description;

    private Integer levelId;

    @NotBlank(message = "phoneNumber is mandatory")
    private String phoneNumber;

}