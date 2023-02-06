package com.kurkus.kusinsa.integration.product;


import javax.transaction.Transactional;


import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.dto.request.product.ProductCreateRequest;
import com.kurkus.kusinsa.dto.request.product.ProductSearchCondition;
import com.kurkus.kusinsa.dto.request.product.ProductUpdateRequest;
import com.kurkus.kusinsa.enums.ProductType;
import com.kurkus.kusinsa.enums.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

//@Testcontainers
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class ProductIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

//    @Container
//    static DockerComposeContainer composeContainer =
//            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
//                    .withExposedService("redis-session", 6379,
//                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));

    private MockHttpSession mockHttpSession = new MockHttpSession();
    private final String URI = "/products";




    @Nested
    class save{

        private String name = "존재안하는상품";
        private Long price = 1000L;
        private String content = "상품1의 설명";
        private Long categoryId = 1L;
        private Long brandId = 1L;
        private String originImagePath = "원본경로1";
        private String thumbnailImagePath = "썸네일경로1";

        private ProductCreateRequest getCreateRequest() {
            return ProductCreateRequest.builder()
                    .name(name)
                    .price(price)
                    .content(content)
                    .categoryId(categoryId)
                    .brandId(brandId)
                    .originImagePath(originImagePath)
                    .thumbnailImagePath(thumbnailImagePath)
                    .build();
        }

        @Test
        public void 성공() throws Exception {
            // given
            adminLogin();
            // when
            ResultActions result = mvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getCreateRequest()))
                    .session(mockHttpSession)).andDo(print());
            // then
            result.andExpect(status().isCreated());
        }

        @Test
        public void 실패_권한없는경우() throws Exception {
            // given
            userLogin();
            // when
            ResultActions result = mvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getCreateRequest()))
                    .session(mockHttpSession)).andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string(FORBIDDEN));
        }

        @Test
        public void 실패_이미존재하는경우() throws Exception {
            // given
            adminLogin();
            name = "상품1";
            // when
            ResultActions result = mvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getCreateRequest()))
                    .session(mockHttpSession)).andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string(EXISTS_PRODUCT));
        }

        @Test
        public void 실패_카테고리_x() throws Exception {
            // given
            adminLogin();
            categoryId = 1000L;
            // when
            ResultActions result = mvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getCreateRequest()))
                    .session(mockHttpSession)).andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string(NOT_FOUND_CATEGORY));
        }
        @Test
        public void 실패_브랜드_x() throws Exception {
            // given
            adminLogin();
            brandId = 1000L;
            // when
            ResultActions result = mvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getCreateRequest()))
                    .session(mockHttpSession)).andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string(NOT_FOUND_BRAND));
        }

    }


    @Nested
    class findById{

        @Test
        public void 성공() throws Exception {
            // given
            String productId = "/1";
            // when
            ResultActions result = mvc.perform(get(URI + productId))
                    .andDo(print());
            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1));
        }

        @Test
        @DisplayName("아이디가 존재하지않아 실패")
        public void 실패() throws Exception {
            // given
            String productId = "/1000";
            // when
            ResultActions result = mvc.perform(get(URI + productId))
                    .andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string(NOT_FOUND_PRODUCT));
        }

    }

    @Nested
    class update {

        private ProductUpdateRequest getUpdateRequest(){
            return ProductUpdateRequest.builder()
                    .name("변경1")
                    .content("내용변경1")
                    .price(20000L)
                    .stock(2000L)
                    .status(ProductType.SOLD_OUT)
                    .build();
        }

        @Test
        @DisplayName("SALE -> SOLD_OUT 변경")
        public void 성공() throws Exception {
            // given
            adminLogin();
            String productId = "/1";
            // when
            ResultActions result = mvc.perform(patch(URI + productId).contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getUpdateRequest()))
                            .session(mockHttpSession))
                    .andDo(print());
            // then
            result.andExpect(status().isOk());
        }
        @Test
        @DisplayName("product Id가 존재하지않는경우")
        public void 실패() throws Exception {
            // given
            adminLogin();
            String productId = "/100000";
            // when
            ResultActions result = mvc.perform(patch(URI + productId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getUpdateRequest()))
                            .session(mockHttpSession))
                    .andDo(print());
            // then
            result.andExpect(status().is4xxClientError())
                    .andExpect(content().string(NOT_FOUND_PRODUCT));
        }
    }

//    @Test
//    @DisplayName("데이터 응답에서 categoryId가 맞게들어가는지 확인합니다")
//    public void findAllByCategory() throws Exception {
//        // given
//        ProductSearchCondition request = ProductSearchCondition.builder()
//                .id(1L)
//                .page(0)
//                .sortProperty("name")
//                .build();
//        // when
//        ResultActions result = mvc.perform(get(URI + "/categories").contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andDo(print());
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].categoryResponse.name").value("맨투맨"));
//    }

    
    private void adminLogin(){
        mockHttpSession.setAttribute(SESSION_ID, 20L);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.ADMIN);
    }

    private void userLogin(){
        mockHttpSession.setAttribute(SESSION_ID, 18L);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
    }



}
