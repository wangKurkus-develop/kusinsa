package com.kurkus.kusinsa.integration.order;


import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.enums.DeliveryStatus;
import com.kurkus.kusinsa.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class OrderUpdateIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    MockHttpSession mockHttpSession = new MockHttpSession();
    private final String URI = "/orders";
    private Long userId = 20L;

    private void adminLogin(){
        mockHttpSession.setAttribute(SESSION_ID, userId);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.ADMIN);
    }

    @Test
    public void updateDeliveryStatus() throws Exception {
        // given
        adminLogin();
        String orderHistoryId = "/5";
        String requestURI = URI+"/delivery"+orderHistoryId;
        DeliveryStatus paramDeliveryStatus = DeliveryStatus.DELIVERY_COMPLETE;
        // when
        ResultActions result = mockMvc.perform(patch(requestURI)
                        .param("delivery", paramDeliveryStatus.toString())
                        .session(mockHttpSession))
                .andDo(print());
        // then
        result.andExpect(status().is2xxSuccessful());
    }
}
