package com.springbootcommunitydevproj.usermanagement;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class UserManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUpMock() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("GET /api/admin/user_list 테스트")
    void getAllUserManagementInfoTest() throws Exception{
        // given
        // 미리 데이터베이스에 세팅한 데이터를 기준으로 한다. (회원 12명)
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
        ResultActions result = mockMvc.perform(get("/api/admin/user_list?page=1")
            .accept(MediaType.APPLICATION_JSON));

        // then
        // 결과 status code 확인
        result.andExpect(status().isOk());
        // 결과 내용 일치 확인 (첫 번째 데이터 전체와 5번째 데이터 currentPostCount 확인)
        result.andExpectAll(jsonPath("$[0].userId").value(answer.get(0).get("userId")),
            jsonPath("$[0].nickname").value(answer.get(0).get("nickname")),
            jsonPath("$[0].levelName").value(answer.get(0).get("levelName")),
            jsonPath("$[0].currentPostCount").value(answer.get(0).get("currentPostCount")),
            jsonPath("$[4].currentPostCount").value(answer.get(4).get("currentPostCount")));
    }

    @Test
    @DisplayName("GET /api/admin/user/{nickname} 테스트")
    void getUserManagementInfoByNicknameTest() {
        // given
        // 테스트 해볼 닉네임들
        List<String> nicknames = List.of("Test 1", "Test 5", "Test 9", "Test 12", "Test 100");

        nicknames.forEach(nickname -> {
            try {
                // when
                ResultActions result = mockMvc.perform(get("/api/admin/user/" + nickname)
                        .accept(MediaType.APPLICATION_JSON));

                // then
                if (nickname.equals("Test 100")) {
                    // Test 100 이란 닉네임은 존재하지 않습니다.
                    result.andExpect(status().isOk()).
                        andExpect(jsonPath("$[0]").doesNotExist());
                }
                else {
                    // 동일한 닉네임인지 확인합니다.
                    result.andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].nickname", containsString(nickname)));
                }
            } catch (Exception e) {
            }
        });
    }
}
