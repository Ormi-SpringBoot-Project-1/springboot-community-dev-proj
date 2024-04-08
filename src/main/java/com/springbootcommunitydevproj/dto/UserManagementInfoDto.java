package com.springbootcommunitydevproj.dto;

/**
 *      Admin 페이지에서 회원 관리 페이지에 출력할 회원 정보를 가져오기 위한 DTO입니다.
 */

public interface UserManagementInfoDto {

    Integer getUserId();
    String getNickname();
    String getLevelName();
    Integer getCurrentPostCount();
}
