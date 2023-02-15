package com.kurkus.kusinsa.integration.notification;


import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.DockerComposeContainerInitializer;
import com.kurkus.kusinsa.dto.request.notification.NotificationCreateRequest;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(initializers = {DockerComposeContainerInitializer.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotificationIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String URI = "/notifications";
    private Long userId = 18L;
    MockHttpSession mockHttpSession = new MockHttpSession();

    private void userLogin() {
        mockHttpSession.setAttribute(SESSION_ID, userId);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
    }

    @Test
    @Order(1)
    public void save() throws Exception {
        // given
        userLogin();
        NotificationCreateRequest request = new NotificationCreateRequest(11L);
        // when
        ResultActions result = mockMvc.perform(post(URI).session(mockHttpSession).content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        result.andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void save_중복신청() throws Exception {
        // given
        userLogin();
        NotificationCreateRequest request = new NotificationCreateRequest(11L);
        // when
        ResultActions result = mockMvc.perform(post(URI).session(mockHttpSession).content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        result.andExpect(status().is4xxClientError())
                .andExpect(content().string(ErrorMessages.DUPLICATE_APPLY));
    }
}
