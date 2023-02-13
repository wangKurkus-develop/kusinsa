package com.kurkus.kusinsa.integration.product;


import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.dto.request.product.status.ProductStockUpdateRequest;
import com.kurkus.kusinsa.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class ProductStatusIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private MockHttpSession mockHttpSession = new MockHttpSession();

    private final String URI = "/products/status";
    private Long userId = 20L;

    private void adminLogin(){
        mockHttpSession.setAttribute(SESSION_ID, userId);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.ADMIN);
    }

    @Test
    public void updateStock() throws Exception {
        // given
        adminLogin();
        ProductStockUpdateRequest request = new ProductStockUpdateRequest(5L, 3);
        String requestURI = URI + "/stock";
        // when
        ResultActions result = mockMvc.perform(patch(requestURI).session(mockHttpSession).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andDo(print());
        // then
        result.andExpect(status().isOk());
    }
}
