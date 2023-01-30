package com.kurkus.kusinsa.integration;


import javax.transaction.Transactional;

import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.dto.request.notification.NotificationCreateRequest;
import com.kurkus.kusinsa.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class NotificationController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String URI = "/notifications";
    private Long userId = 18L;
    MockHttpSession mockHttpSession = new MockHttpSession();

    private void userLogin(){
        mockHttpSession.setAttribute(SESSION_ID, userId);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
    }


    @Test
    public void 저장() throws Exception {
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
    public void 저장_그룹이존재하는경우() throws Exception {
        // given
        userId = 19L;
        userLogin();
        NotificationCreateRequest request = new NotificationCreateRequest(11L);
        // when
        ResultActions result = mockMvc.perform(post(URI).session(mockHttpSession).content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        result.andExpect(status().isCreated());
    }
}
