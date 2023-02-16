package com.kurkus.kusinsa.integration;


import javax.transaction.Transactional;
import javax.validation.constraints.Min;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.kurkus.kusinsa.DockerComposeContainerInitializer;
import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(initializers = {DockerComposeContainerInitializer.class})
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
    class findAll {

        @Test
        public void Division_ALL() throws Exception {
            // given
            loginUser();
            PointType type = PointType.ALL;
            PointSearchCondition request = new PointSearchCondition(type, TEST_USER_ID);
            // when
            ResultActions result = mockMvc.perform(get(URI)
                    .session(mockHttpSession)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print());
            // then
            result.andExpect(status().isOk());
        }

        @Test
        public void Division_USED() throws Exception {
            // given
            loginUser();
            PointType type = PointType.USED;
            PointSearchCondition request = new PointSearchCondition(type, TEST_USER_ID);
            // when
            ResultActions result = mockMvc.perform(get(URI).session(mockHttpSession)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print());
            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].division").value("USED"));
        }

        @Test
        public void Division_OBTAIN() throws Exception {
            // given
            loginUser();
            PointType type = PointType.OBTAIN;
            PointSearchCondition request = new PointSearchCondition(type, TEST_USER_ID);
            // when
            ResultActions result = mockMvc.perform(get(URI)
                            .session(mockHttpSession)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print());
            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].division").value("OBTAIN"));
        }

    }

}
