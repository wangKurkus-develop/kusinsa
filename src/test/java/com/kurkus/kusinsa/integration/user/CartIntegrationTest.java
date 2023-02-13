package com.kurkus.kusinsa.integration.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class CartIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String URI = "/carts";
    private Long userId = 18L;
    MockHttpSession mockHttpSession = new MockHttpSession();

    private void userLogin(){
        mockHttpSession.setAttribute(SESSION_ID, userId);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
    }

    @Test
    public void 상품좋아요() throws Exception {
        // given
        String requestURI = URI+"/likes/1";
        userLogin();
        // when
        ResultActions result = mockMvc.perform(post(requestURI).session(mockHttpSession))
                .andDo(print());
        // then
        result.andExpect(status().is2xxSuccessful());
    }

    @Test
    public void 상품좋아요취소() throws Exception {
        // given
        String requestURI = URI+"/likes/1";
        userLogin();
        // when
        ResultActions result = mockMvc.perform(delete(requestURI).session(mockHttpSession))
                .andDo(print());
        // then
        result.andExpect(status().is2xxSuccessful());
    }


}
