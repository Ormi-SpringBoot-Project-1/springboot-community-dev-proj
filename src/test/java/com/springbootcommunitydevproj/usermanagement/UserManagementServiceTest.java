package com.springbootcommunitydevproj.usermanagement;

import static org.assertj.core.api.Assertions.assertThat;

import com.springbootcommunitydevproj.dto.UserManagementInfoDto;
import com.springbootcommunitydevproj.service.UserManagementService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserManagementServiceTest {

    @Autowired
    private UserManagementService userManagementService;

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
    @DisplayName("getUserManagementInfoByNickname(String nickname) 테스트")
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
            List<UserManagementInfoDto> result = userManagementService.getUserManagementInfoByNickname(nicknames.get(i));

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
}
