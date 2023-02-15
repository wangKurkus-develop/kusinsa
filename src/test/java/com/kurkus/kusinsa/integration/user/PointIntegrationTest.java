package com.kurkus.kusinsa.integration.user;


import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.DockerComposeContainerInitializer;
import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {DockerComposeContainerInitializer.class})
public class PointIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    MockHttpSession mockHttpSession = new MockHttpSession();
    private final String URI = "/points";
    private final Long userId = 18L;

    private void userLogin() {
        mockHttpSession.setAttribute(SESSION_ID, userId);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
    }

    @Test
    public void findAll() throws Exception {
        // given
        userLogin();
        PointSearchCondition request = new PointSearchCondition(PointType.ALL, userId);
        // when
        ResultActions result = mockMvc.perform(get(URI).session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());
        // then
        result.andExpect(status().is2xxSuccessful());
    }
}
