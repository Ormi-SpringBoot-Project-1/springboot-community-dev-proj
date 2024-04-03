package com.springbootcommunitydevproj.usermanagement;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.springbootcommunitydevproj.utils.ResponseMessages;
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
        ResultActions result = mockMvc.perform(get("/api/admin/user_list?page=1&order=userId&sort=asc")
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

//    @Test
//    @DisplayName("GET /api/admin/user/{nickname} 테스트")
//    void getUserManagementInfoByNicknameTest() {
//        // given
//        // 테스트 해볼 닉네임들
//        List<String> nicknames = List.of("Test 1", "Test 5", "Test 9", "Test 12", "Test 100");
//
//        nicknames.forEach(nickname -> {
//            try {
//                // when
//                ResultActions result = mockMvc.perform(get("/api/admin/user/" + nickname)
//                        .accept(MediaType.APPLICATION_JSON));
//
//                // then
//                if (nickname.equals("Test 100")) {
//                    // Test 100 이란 닉네임은 존재하지 않습니다.
//                    result.andExpect(status().isOk()).
//                        andExpect(jsonPath("$[0]").doesNotExist());
//                }
//                else {
//                    // 동일한 닉네임인지 확인합니다.
//                    result.andExpect(status().isOk())
//                        .andExpect(jsonPath("$[0].nickname", containsString(nickname)));
//                }
//            } catch (Exception e) {
//            }
//        });
//    }

    @Test
    @DisplayName("GET /api/admin/user/{nickname} 테스트")
    void getUserManagementInfoByNicknameTest() {


    }

    @Test
    @DisplayName("PUT /api/admin/user/level 테스트")
    void changeUserLevelTest() throws Exception{
        // given
        String jsonTestData1 = "{\"userId\": 5,\"level\": 3}"; // 성공 데이터
        String jsonTestData2 = "{\"user\":2,\"level\":1}";  // 실패 데이터
        String jsonTestData3 = "{\"userId\":100,\"level\":3}"; // 실패 데이터
        String jsonTestData4 = "{\"userId\":7,\"level\":0}"; // 비유효 데이터

        // when
        ResultActions result1 = mockMvc.perform(put("/api/admin/user/level")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonTestData1));
        ResultActions result2 = mockMvc.perform(put("/api/admin/user/level")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonTestData2));
        ResultActions result3 = mockMvc.perform(put("/api/admin/user/level")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonTestData3));
        ResultActions result4 = mockMvc.perform(put("/api/admin/user/level")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonTestData4));

        // then
        // Status Code를 확인하고 성공, 실패, 비유효 메시지를 정상적으로 올바르게 받았는지 확인합니다.
        result1.andExpect(status().isOk());
        result1.andExpect(jsonPath("$.result").value(ResponseMessages.CHANGE_LEVEL_SUCCESS));

        result2.andExpect(status().isOk());
        result2.andExpect(jsonPath("$.result").value(ResponseMessages.CHANGE_LEVEL_FAIL));

        result3.andExpect(status().isOk());
        result3.andExpect(jsonPath("$.result").value(ResponseMessages.CHANGE_LEVEL_FAIL));

        result4.andExpect(status().isOk());
        result4.andExpect(jsonPath("$.result").value(ResponseMessages.INVALID_LEVEL));
    }

    @Test
    @DisplayName("POST /api/admin/user/blocked 테스트")
    void setUserToBlockedUserTest() throws Exception{
        // given
        String testData1 = "{\"userId\":1}"; // 성공
        String testData2 = "{\"userId\":1}"; // 실패 (이미 설정됨)
        String testData3 = "{\"userId\":100}"; // 실패 (없는 회원)

        // when
        ResultActions result1 = mockMvc.perform(post("/api/admin/user/blocked")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testData1));
        ResultActions result2 = mockMvc.perform(post("/api/admin/user/blocked")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testData2));
        ResultActions result3 = mockMvc.perform(post("/api/admin/user/blocked")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testData3));

        // then
        // Status Code를 확인하고 성공, 실패 메시지를 정상적으로 올바르게 받았는지 확인합니다.
        result1.andExpect(status().isCreated())
            .andExpect(jsonPath("$.result").value(ResponseMessages.SET_USER_TO_BLOCKED_USER_SUCCESS));
        result2.andExpect(status().isCreated())
            .andExpect(jsonPath("$.result").value(ResponseMessages.ALREADY_SET_BLOCKED_USER));
        result3.andExpect(status().isCreated())
            .andExpect(jsonPath("$.result").value(ResponseMessages.SET_USER_TO_BLOCKED_USER_FAIL));
    }

    @Test
    @DisplayName("DELETE /api/admin/user/unblocked 테스트")
    void setBlockUserToUnblockTest() throws Exception{
        // given
        String testData = "{\"userId\":1}";

        // when
        ResultActions result = mockMvc.perform(delete("/api/admin/user/unblocked")
            .contentType(MediaType.APPLICATION_JSON)
            .content(testData));

        // then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value(ResponseMessages.USER_UNBLOCK_SUCCESS));
    }
}
