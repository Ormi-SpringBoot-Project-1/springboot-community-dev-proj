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
    public void findAllUserManagementInfoTest() {
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
            assertThat(result.get(i).getUserId()).isEqualTo(Integer.parseInt(answer.get(i).get("userId")));
            assertThat(result.get(i).getNickname()).isEqualTo(answer.get(i).get("nickname"));
            assertThat(result.get(i).getLevelName()).isEqualTo(answer.get(i).get("levelName"));
            assertThat(result.get(i).getCurrentPostCount()).isEqualTo(Integer.parseInt(answer.get(i).get("currentPostCount")));
        }
    }
}
