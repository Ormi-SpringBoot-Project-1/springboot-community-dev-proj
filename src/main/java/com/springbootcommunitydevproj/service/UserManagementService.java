package com.springbootcommunitydevproj.service;

import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.repository.UserManagementRepository;
import java.util.List;
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
}
