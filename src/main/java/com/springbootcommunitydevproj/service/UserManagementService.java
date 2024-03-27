package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.repository.UserManagementRepository;
import com.springbootcommunitydevproj.utils.ResponseMessages;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserManagementService {

    private final UserManagementRepository userManagementRepository;

    // 한 페이지 당 30개의 검색 결과가 기준이므로 offset = (page - 1) * 30이 됩니다.
    public List<UserManagementInfoDto> getAllUserManagementInfo(Integer page) {
        return userManagementRepository.findAllUserManagementInfo((page - 1) * 30);
    }

    // 회원의 닉네임을 기준으로 해당 회원 정보를 조회합니다.
    // 조회 결과가 없으면 빈 리스트를 반환합니다.
    public List<UserManagementInfoDto> getUserManagementInfoByNickname(String nickname) {
        return userManagementRepository.findByNickname(nickname);
    }

    // 특정 회원의 등급을 변경합니다.
    public String changeUserLevel(Map<String, Integer> request) {
        // request에 회원의 user_id가 없거나 변경할 level이 없으면 변경 실패 메시지를 반환합니다.
        if (!request.containsKey("user_id") || !request.containsKey("level")) {
            return ResponseMessages.CHANGE_LEVEL_FAIL;
        }

        // 만약 변경하고자 하는 level이 올바른 범위(5 ~ 1)를 벗어나면 유효하지 않은 등급 메시지를 반환합니다.
        if (request.get("level") <= 0 || 5 < request.get("level")) {
            return ResponseMessages.INVALID_LEVEL;
        }

        Integer result =  userManagementRepository.changeUserLevel(request.get("user_id"), request.get("level"));

        // 쿼리 실행 결과에 따라 성공, 실패 메시지를 반환합니다.
        return result.equals(1) ? ResponseMessages.CHANGE_LEVEL_SUCCESS : ResponseMessages.CHANGE_LEVEL_FAIL;
    }
}
