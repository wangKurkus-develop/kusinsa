package com.kurkus.kusinsa.integration;


import javax.transaction.Transactional;
import javax.validation.constraints.Min;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.response.point.PointResponse;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.utils.constants.PointMessages;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class PointIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private MockHttpSession mockHttpSession = new MockHttpSession();
    private final String URI = "/points";

    private final Long TEST_USER_ID = 18L;
    private final Long TEST_ADMIN_ID = 20L;

    public void loginUser() {
        mockHttpSession.setAttribute(SESSION_ID, TEST_USER_ID);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
    }

    public void loginAdmin() {
        mockHttpSession.setAttribute(SESSION_ID, TEST_ADMIN_ID);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.ADMIN);
    }

    @Nested
    class save {

        @Test
        public void 성공() throws Exception {
            // given
            Long score = 10000L;
            String content = PointMessages.ORDER_USED_CONTENT;
            PointType division = PointType.USED;
            loginUser();
            PointCreateRequest request = new PointCreateRequest(score, content, division);
            // when
            ResultActions result = mockMvc.perform(post(URI).session(mockHttpSession).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))).andDo(print());
            // then
            result.andExpect(status().isCreated());
        }

        @Test
        public void 실패_UserNotFound() throws Exception {
            // given
            Long score = 10000L;
            String content = PointMessages.ORDER_USED_CONTENT;
            PointType division = PointType.USED;
            PointCreateRequest request = new PointCreateRequest(score, content, division);

            mockHttpSession.setAttribute(SESSION_ID, 111111L);
            mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
            // when
            ResultActions result = mockMvc.perform(post(URI).session(mockHttpSession).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))).andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string(NOT_FOUND_USER));
        }

        @Test
        public void point_최소값_만족안하는경우() throws Exception {
            // given
            Long score = 1L;
            String content = PointMessages.ORDER_USED_CONTENT;
            PointType division = PointType.USED;
            PointCreateRequest request = new PointCreateRequest(score, content, division);
            loginUser();
            // when
            ResultActions result = mockMvc.perform(post(URI).session(mockHttpSession).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))).andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string("적립금 전환은 1,000점 이상부터 가능합니다"));
        }

    }

    @Nested
    class findAll {

        @Test
        public void 조건없이요청한경우() throws Exception {
            // given
            loginUser();
            // when
            ResultActions result = mockMvc.perform(get(URI).session(mockHttpSession)).andDo(print());
            // then
            result.andExpect(status().isOk());
        }

        @Test
        public void Division_USED() throws Exception {
            // given
            loginUser();
            String division = "USED";
            // when
            ResultActions result = mockMvc.perform(get(URI).session(mockHttpSession).param("division", division))
                    .andDo(print());
            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].division").value(division));
        }

        @Test
        public void Division_OBTAIN() throws Exception {
            // given
            loginUser();
            String division = "OBTAIN";
            // when
            ResultActions result = mockMvc.perform(get(URI).session(mockHttpSession).param("division", division))
                    .andDo(print());
            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].division").value(division));
        }

        @Test
        public void 페이지_음수인경우() throws Exception {
            // given
            String page = "-1";
            loginUser();
            // when
            mockMvc.perform(get(URI).session(mockHttpSession).param("page", page))
                    .andDo(print());
            // then
        }
    }

}
