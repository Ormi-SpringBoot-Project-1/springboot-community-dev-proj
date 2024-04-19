package com.springbootcommunitydevproj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *      회원 정보의 조회에 대한 결과를 반환하기 위한 DTO입니다.
 */

@Getter
@Setter
@AllArgsConstructor
public class UserManagementResponse {
    private String result;
}
