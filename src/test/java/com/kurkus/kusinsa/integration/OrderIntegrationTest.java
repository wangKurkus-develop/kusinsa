package com.kurkus.kusinsa.integration;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.DockerComposeContainerInitializer;
import com.kurkus.kusinsa.dto.request.order.OrderCreateRequest;
import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private final Long userId = 18L;

    private void userLogin(){
        mockHttpSession.setAttribute(SESSION_ID, userId);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
    }

    @Nested
    class save{
        Long productId= 12L;
        long obtainPoint = 200;
        int quantity = 1;
        long price = 30000L;
        //
        long orderPrice = 20000L;
        long totalObtainPoint = 200;
        long totalUsedPoint = 10000;
        String deliveryAddress = "테스트배송지1";

        @Test
        public void 성공() throws Exception {
            // given
            userLogin();
            List<OrderProductRequest> requestList = new ArrayList<>();
            OrderProductRequest orderProduct = OrderProductRequest.builder()
                    .productId(productId)
                    .obtainPoint(obtainPoint)
                    .quantity(quantity)
                    .price(price)
                    .build();
            requestList.add(orderProduct);

            OrderCreateRequest request = OrderCreateRequest.builder()
                    .orderProductRequestList(requestList)
                    .orderPrice(orderPrice)
                    .totalUsedPoint(totalUsedPoint)
                    .totalObtainPoint(totalObtainPoint)
                    .deliveryAddress(deliveryAddress)
                    .build();
            // when
            ResultActions result = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))
                    .session(mockHttpSession)).andDo(print());
            // then
            result.andExpect(status().isCreated());
        }

        @Test
        public void 실패_재고없는경우_기존140개() throws Exception {
            // given
            quantity = 1000;
            userLogin();
            List<OrderProductRequest> requestList = new ArrayList<>();
            OrderProductRequest orderProduct = OrderProductRequest.builder()
                    .productId(productId)
                    .obtainPoint(obtainPoint)
                    .quantity(quantity)
                    .price(price)
                    .build();
            requestList.add(orderProduct);

            OrderCreateRequest request = OrderCreateRequest.builder()
                    .orderProductRequestList(requestList)
                    .orderPrice(orderPrice)
                    .totalUsedPoint(totalUsedPoint)
                    .totalObtainPoint(totalObtainPoint)
                    .deliveryAddress(deliveryAddress)
                    .build();
            // when
            ResultActions result = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))
                    .session(mockHttpSession)).andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string("이름1 "+NOT_ENOUGH_QUANTITY));
        }

        @Test
        public void 실패_품절인경우() throws Exception {
            // given
            productId = 11L;
            userLogin();
            List<OrderProductRequest> requestList = new ArrayList<>();
            OrderProductRequest orderProduct = OrderProductRequest.builder()
                    .productId(productId)
                    .obtainPoint(obtainPoint)
                    .quantity(quantity)
                    .price(price)
                    .build();
            requestList.add(orderProduct);

            OrderCreateRequest request = OrderCreateRequest.builder()
                    .orderProductRequestList(requestList)
                    .orderPrice(orderPrice)
                    .totalUsedPoint(totalUsedPoint)
                    .totalObtainPoint(totalObtainPoint)
                    .deliveryAddress(deliveryAddress)
                    .build();
            // when
            ResultActions result = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))
                    .session(mockHttpSession)).andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string("이름0 "+NOT_SALE));
        }


    }


    @Nested
    class orderCancel{

        
        @Test
        public void 성공() throws Exception {
            // given
            long historyId = 3;
            String requestURI = URI + "/"+historyId;
            userLogin();
            // when
            ResultActions result = mockMvc.perform(patch(requestURI).session(mockHttpSession).contentType(MediaType.APPLICATION_JSON))
                    .andDo(print());
            // then
            result.andExpect(status().is2xxSuccessful());
        }
        @Test
        public void 실패_NOT_AUTHORITY() throws Exception {
            // given
            long historyId = 4;
            String requestURI = URI + "/"+historyId;
            userLogin();
            // when
            ResultActions result = mockMvc.perform(patch(requestURI).session(mockHttpSession).contentType(MediaType.APPLICATION_JSON))
                    .andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string(NOT_AUTHORITY));
        }

        @Test
        public void 실패_FAIL_CANCEL_DELIVERY() throws Exception {
            // given
            Long historyId = 5L;
            String requestURI = URI + "/"+historyId;
            userLogin();
            // when
            ResultActions result = mockMvc.perform(patch(requestURI).session(mockHttpSession).contentType(MediaType.APPLICATION_JSON))
                    .andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string(FAIL_CANCEL_DELIVERY));
        }
    }



    @Nested
    class findAll{
        @Test
        public void 성공() throws Exception {
            // given
            userLogin();
            // when
            ResultActions result = mockMvc.perform(get(URI).session(mockHttpSession))
                    .andDo(print());
            // then
            result.andExpect(status().is2xxSuccessful());
        }

    }



}
