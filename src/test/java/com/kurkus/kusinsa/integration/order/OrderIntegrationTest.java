package com.kurkus.kusinsa.integration.order;


import java.util.ArrayList;
import java.util.List;

import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.DockerComposeContainerInitializer;
import com.kurkus.kusinsa.dto.request.order.OrderCreateRequest;
import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
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

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(initializers = {DockerComposeContainerInitializer.class})
public class OrderIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    MockHttpSession mockHttpSession = new MockHttpSession();

    private final String URI = "/orders";
    private Long userId = 18L;

    private void userLogin(){
        mockHttpSession.setAttribute(SESSION_ID, userId);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
    }

    @Test
    public void save() throws Exception {
        // given
        userLogin();
        List<OrderProductRequest> list = new ArrayList<>();
        OrderProductRequest order1 = new OrderProductRequest(12L, 2, 10000L, 0);
        list.add(order1);
        OrderCreateRequest request = new OrderCreateRequest(list, 10000L, 0,
                0, "주소1");
        // when
        ResultActions result = mockMvc.perform(post(URI).session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());
        // then
        result.andExpect(status().isCreated());
    }

    @Test
    public void save_수량모자른경우() throws Exception {
        // given
        userLogin();
        List<OrderProductRequest> list = new ArrayList<>();
        OrderProductRequest order1 = new OrderProductRequest(12L, 20000, 10000L, 0);
        list.add(order1);
        OrderCreateRequest request = new OrderCreateRequest(list, 10000L, 0,
                0, "주소1");
        // when
        ResultActions result = mockMvc.perform(post(URI).session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());
        // then
        result.andExpect(status().is4xxClientError())
                .andExpect(content().string("이름1 "+ ErrorMessages.NOT_ENOUGH_QUANTITY));
    }

    @Test
    public void save_판매아닌경우() throws Exception {
        userLogin();
        List<OrderProductRequest> list = new ArrayList<>();
        OrderProductRequest order1 = new OrderProductRequest(11L, 2, 10000L, 0);
        list.add(order1);
        OrderCreateRequest request = new OrderCreateRequest(list, 10000L, 0,
                0, "주소1");
        // when
        ResultActions result = mockMvc.perform(post(URI).session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print());
        // then
        result.andExpect(status().is4xxClientError())
                .andExpect(content().string("이름0 "+ErrorMessages.NOT_SALE));
    }

    @Test
    public void findAll() throws Exception {
        // given
        userLogin();
        // when
        ResultActions result = mockMvc.perform(get(URI).session(mockHttpSession))
                .andDo(print());
        // then
        result.andExpect(status().is2xxSuccessful());
    }

    @Test
    public void orderCancel() throws Exception {
        // given
        userLogin();
        String historyId = "/6";
        // when
        ResultActions result = mockMvc.perform(patch(URI +historyId)
                        .session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        result.andExpect(status().is2xxSuccessful());
    }

    @Test
    public void orderCancel_이미배달중인경우실패() throws Exception {
        // given
        userLogin();
        String historyId = "/5";
        // when
        ResultActions result = mockMvc.perform(patch(URI + historyId)
                        .session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        result.andExpect(status().is4xxClientError())
                .andExpect(content().string(ErrorMessages.FAIL_CANCEL_DELIVERY));
    }
}
