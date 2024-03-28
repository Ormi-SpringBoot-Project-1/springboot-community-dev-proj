package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.dto.UserRequest;
import com.springbootcommunitydevproj.repository.BlockedUserRepository;
import com.springbootcommunitydevproj.repository.UserManagementRepository;
import com.springbootcommunitydevproj.utils.ResponseMessages;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserManagementService {

    private final UserManagementRepository userManagementRepository;
    private final BlockedUserRepository blockedUserRepository;

    // 한 페이지 당 10개의 검색 결과가 기준이므로 offset = (page - 1) * 10이 됩니다.
    // (3/28 추가) 만약 page가 음수거나 정렬할 열의 이름, 정렬 순서가 올바르지 않다면 userId 기준 오름차순으로 첫 번째 페이지의 목록을 가져옵니다.
    public List<UserManagementInfoDto> getAllUserManagementInfo(Integer page, String orderBy, String sort) {
        PageRequest pageRequest;

        if (!(orderBy.equals("userId") || orderBy.equals("nickname")
            || orderBy.equals("level") || orderBy.equals("currentPostCount")
            || sort.equals("asc") || sort.equals("desc"))
            || page < 0) {
            pageRequest = PageRequest.of(0, 10, Direction.ASC, "userId");

            return userManagementRepository.findAllUserManagementInfo(0, pageRequest);
        }

        if (sort.equals("asc")) {
            pageRequest = PageRequest.of(0, 10, Direction.ASC, orderBy);
        }
        else {
            pageRequest = PageRequest.of(0, 10, Direction.DESC, orderBy);
        }

        return userManagementRepository.findAllUserManagementInfo((page - 1) * 10, pageRequest);
    }

    // 회원의 닉네임을 기준으로 해당 회원 정보를 조회합니다.
    // 조회 결과가 없으면 빈 리스트를 반환합니다.
    public List<UserManagementInfoDto> getUserManagementInfoByNickname(String nickname) {
        return userManagementRepository.findByNickname(nickname);
    }

    // 특정 회원의 등급을 변경합니다.
    public String changeUserLevel(UserRequest request) {
        // 만약 변경하고자 하는 level이 올바른 범위(5 ~ 1)를 벗어나면 유효하지 않은 등급 메시지를 반환합니다.
        if (request.getLevel() <= 0 || 5 < request.getLevel()) {
            return ResponseMessages.INVALID_LEVEL;
        }

        Integer result = userManagementRepository.changeUserLevel(request.getUserId(), request.getLevel());

        // 쿼리 실행 결과에 따라 성공, 실패 메시지를 반환합니다.
        return result.equals(1) ? ResponseMessages.CHANGE_LEVEL_SUCCESS : ResponseMessages.CHANGE_LEVEL_FAIL;
    }

    // 특정 회원을 가입 제한으로 설정합니다.
    public String setUserToBlockedUser(UserRequest request) {
        // 이미 가입 제한 테이블에 해당 회원이 존재하는지 확인합니다.
        if (blockedUserRepository.findById(request.getUserId()).isPresent()) {
            return ResponseMessages.ALREADY_SET_BLOCKED_USER;
        }

        Integer result = blockedUserRepository.moveUserToBlockedUser(request.getUserId());

        // 쿼리 실행 결과에 따라 성공, 실패 메시지를 반환합니다.
        return result.equals(1) ? ResponseMessages.SET_USER_TO_BLOCKED_USER_SUCCESS
            : ResponseMessages.SET_USER_TO_BLOCKED_USER_FAIL;
    }

    // 특정 회원의 가입 제한을 해제합니다.
    public String setBlockedUserToUnblock(UserRequest request) {
        blockedUserRepository.deleteById(request.getUserId());

        return ResponseMessages.USER_UNBLOCK_SUCCESS;
    }
}
