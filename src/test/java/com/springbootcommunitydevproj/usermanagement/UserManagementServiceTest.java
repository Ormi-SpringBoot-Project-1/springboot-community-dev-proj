package com.springbootcommunitydevproj.usermanagement;

import static org.assertj.core.api.Assertions.assertThat;

import com.springbootcommunitydevproj.domain.BlockedUser;
import com.springbootcommunitydevproj.domain.User;
import com.springbootcommunitydevproj.dto.ChangeUserLevelRequest;
import com.springbootcommunitydevproj.dto.SetUserToBlockedUserRequest;
import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.repository.BlockedUserRepository;
import com.springbootcommunitydevproj.repository.UserManagementRepository;
import com.springbootcommunitydevproj.service.UserManagementService;
import com.springbootcommunitydevproj.utils.ResponseMessages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserManagementServiceTest {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private UserManagementRepository userManagementRepository;

    @Autowired
    private BlockedUserRepository blockedUserRepository;

    @Test
    @DisplayName("findAllUserManagementInfo() 테스트")
    void findAllUserManagementInfoTest() {
        // given
        // 미리 데이터베이스에 세팅한 데이터를 기준으로 합니다. (회원 12명)
        List<Map<String, String>> answer = List.of(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
            new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
            new HashMap<>(), new HashMap<>());
        for (int i = 0; i < answer.size(); i++) {
            answer.get(i).put("userId", Integer.toString(i + 1));
            answer.get(i).put("nickname", "Test " + (i + 1));
            answer.get(i).put("levelName", "Tier 5");

            if (i == 0 || i == 1 || i == 9) {
                answer.get(i).put("currentPostCount", "1");
            }
            else if (i == 4) {
                answer.get(i).put("currentPostCount", "2");
            }
            else {
                answer.get(i).put("currentPostCount", "0");
            }
        }

        // when
        List<UserManagementInfoDto> result = userManagementService.getAllUserManagementInfo(1);

        // then
        // 결과 갯수 확인
        assertThat(result.size()).isEqualTo(answer.size());
        // 결과 내용 일치 확인
        for (int i = 0; i < answer.size(); i++) {
            UserManagementInfoDto actual = result.get(i);
            Map<String, String> expected = answer.get(i);

            assertThat(actual.getUserId()).isEqualTo(Integer.parseInt(expected.get("userId")));
            assertThat(actual.getNickname()).isEqualTo(expected.get("nickname"));
            assertThat(actual.getLevelName()).isEqualTo(expected.get("levelName"));
            assertThat(actual.getCurrentPostCount()).isEqualTo(Integer.parseInt(expected.get("currentPostCount")));
        }
    }

    @Test
    @DisplayName("getUserManagementInfoByNickname() 테스트")
    void getUserManagementInfoByNicknameTest() {
        // given
        // ID가 1, 5, 9, 12번인 회원 정보를 테스트로 사용합니다.
        List<Map<String, String>> answer = new ArrayList<>();
        List<Integer> indexes = List.of(1, 5, 9, 12);

        indexes.forEach(index -> {
            Map<String, String> testData = new HashMap<>();
            testData.put("userId", Integer.toString(index));
            testData.put("nickname", "Test " + index);
            testData.put("levelName", "Tier 5");

            if (index == 1) {
                testData.put("currentPostCount", "1");
            }
            else if (index == 5) {
                testData.put("currentPostCount", "2");
            }
            else {
                testData.put("currentPostCount", "0");
            }

            answer.add(testData);
        });

        // 테스트 해볼 닉네임들
        List<String> nicknames = List.of("Test 1", "Test 5", "Test 9", "Test 12", "Test 100");

        for (int i = 0; i < nicknames.size(); i++) {
            // when
            List<UserManagementInfoDto> result = userManagementService.getUserManagementInfoByNickname(
                nicknames.get(i));

            // then
            if (i == 4) {
                // Test 100 이란 닉네임은 존재하지 않습니다.
                assertThat(result.size()).isEqualTo(0);
            }
            else {
                // 조회 결과가 1개인지 확인합니다.
                assertThat(result.size()).isEqualTo(1);

                UserManagementInfoDto actual = result.get(0);
                Map<String, String> expected = answer.get(i);

                // 존재하는 닉네임 조회 결과가 일치하는지 확인합니다.
                assertThat(actual.getUserId()).isEqualTo(Integer.parseInt(expected.get("userId")));
                assertThat(actual.getNickname()).isEqualTo(expected.get("nickname"));
                assertThat(actual.getLevelName()).isEqualTo(expected.get("levelName"));
                assertThat(actual.getCurrentPostCount()).isEqualTo(Integer.parseInt(expected.get("currentPostCount")));
            }
        }
    }

    @Test
    @DisplayName("changeUserLevel() 테스트")
    void changeUserLevelTest() {
        // given
        // 총 8개의 데이터로 테스트합니다. (성공: 5개 / 실패: 3개)
        List<ChangeUserLevelRequest> testDataList = List.of(new ChangeUserLevelRequest(1, 4),
            new ChangeUserLevelRequest(2, 3), new ChangeUserLevelRequest(10, 2), new ChangeUserLevelRequest(7, 1),
            new ChangeUserLevelRequest(12, 3), new ChangeUserLevelRequest(112, 3), new ChangeUserLevelRequest(6, 0),
            new ChangeUserLevelRequest(8, 9));

        for (int i = 0; i < testDataList.size(); i++) {
            // when
            // 등급 변경 결과와 검증용 데이터를 가져옵니다.
            String result = userManagementService.changeUserLevel(testDataList.get(i));
            Optional<User> user = userManagementRepository.findById(testDataList.get(i).getUserId());

            // then
            if (i < 5) {
                // 등급 변경 성공 메시지를 정상적으로 받았는지 확인합니다.
                assertThat(result).isEqualTo(ResponseMessages.CHANGE_LEVEL_SUCCESS);
                // 등급이 실제로 올바르게 변경되었는지 확인합니다.
                assertThat(user.orElseThrow().getLevelId()).isEqualTo(testDataList.get(i).getLevel());
            }
            else if (i == 5) {
                // 등급 변경 실페 메시지를 정상적으로 받았는지 확인합니다.
                assertThat(result).isEqualTo(ResponseMessages.CHANGE_LEVEL_FAIL);
            }
            else {
                // 비유효 등급 메시지를 정상적으로 받았는지 확인합니다.
                assertThat(result).isEqualTo(ResponseMessages.INVALID_LEVEL);
            }
        }

    }

    @Test
    @DisplayName("setUserToBlockedUser() 테스트")
    void setUserToBlockedUserTest() {
        // given
        // 6개의 데이터로 테스트합니다. (성공: 3개 / 실패: 3개)
        List<SetUserToBlockedUserRequest> testDataList = List.of(new SetUserToBlockedUserRequest(1),
            new SetUserToBlockedUserRequest(5), new SetUserToBlockedUserRequest(10),
            new SetUserToBlockedUserRequest(100), new SetUserToBlockedUserRequest(-12),
            new SetUserToBlockedUserRequest(5));

        for (int i = 0; i < testDataList.size(); i++) {
            // when
            String result = userManagementService.setUserToBlockedUser(testDataList.get(i));

            // then
            if (i < 3) {
                // 성공 데이터에 대한 결과 메시지가 올바른지 확인합니다.
                assertThat(result).isEqualTo(ResponseMessages.SET_USER_TO_BLOCKED_USER_SUCCESS);
            }
            else if (i < 5) {
                // 실패 데이터에 대한 결과 메시지 올바른지 확인합니다.
                assertThat(result).isEqualTo(ResponseMessages.SET_USER_TO_BLOCKED_USER_FAIL);
            }
            else {
                // 이미 가입 제한으로 설정된 회원 메시지가 올바른지 확인합니다.
                assertThat(result).isEqualTo(ResponseMessages.ALREADY_SET_BLOCKED_USER);
            }
        }

        List<BlockedUser> result = blockedUserRepository.findAll();
        // 실제로 3개가 들어왔는지 확인합니다.
        assertThat(result.size()).isEqualTo(3);
        // 올바르게 데이터가 들어갔는지 확인합니다.
        for (int i = 0; i < 3; i++) {
            assertThat(result.get(i).getUserId()).isEqualTo(testDataList.get(i).getUserId());
        }
    }
}
