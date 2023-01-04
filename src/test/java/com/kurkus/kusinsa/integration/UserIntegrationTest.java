package com.kurkus.kusinsa.integration;


import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import static com.kurkus.kusinsa.utils.constants.ExceptionConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.dto.user.LoginRequestDto;
import com.kurkus.kusinsa.dto.user.SignupRequestDto;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.utils.constants.ExceptionConstants;
import com.kurkus.kusinsa.utils.constants.SessionConstants;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class UserIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    private String email = "cwg@naver.com";
    private String password = "1234";
    private String name = "최왕규";
    private String address = "쿠르쿠스시 쿠르쿠르아파트";
    private String phoneNumber = "010-1111-1111";

    private SignupRequestDto getSignupRequestDto() {
        return SignupRequestDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }

    private LoginRequestDto getLoginRequestDto() {
        return new LoginRequestDto(email, password);
    }


    @Nested
    @DisplayName("회원가입")
    class signup {
        @Test
        public void 성공() throws Exception {
            // given
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions.andExpect(status().isCreated());
        }

        @Test
        public void 이메일_중복() throws Exception {
            // given
            mvc.perform(post("/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getSignupRequestDto())));
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string(EXISTS_EMAIL));
        }

        // not null
        // 이메일형식아닌경우
        @Test
        public void 이메일_NULL() throws Exception {
            // given
            email = null;
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("이메일 형식이 아닙니다"));
        }

        @Test
        public void 이메일_형식x() throws Exception {
            // given
            email = "hello123";
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("이메일 형식이 아닙니다"));
        }


        @Test
        public void 비밀번호_Blank() throws Exception {
            // given
            password = "        ";
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("비밀번호를 입력해주세요"));
        }

        @Test
        public void 비밀번호_최소길이() throws Exception {
            // given
            password = "123";
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("비밀번호는 4글자 이상 16자 이하입니다"));
        }

        @Test
        public void 비밀번호_최대길이() throws Exception {
            // given
            password = "123456789123456899999999999999999";
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("비밀번호는 4글자 이상 16자 이하입니다"));
        }

        @Test
        public void 핸드폰형식_x() throws Exception {
            // given
            phoneNumber = "01011111111";
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("핸드폰 형식이 아닙니다"));
        }

        @Test
        public void 주소입력_x() throws Exception {
            // given
            address = null;
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("주소를 입력해주세요"));
        }


        @Test
        public void 이름입력_x() throws Exception {
            // given
            name = null;
            // when
            ResultActions resultActions = mvc.perform(post("/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getSignupRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string("이름을 입력해주세요"));
        }
    }

    @Nested
    @DisplayName("로그인")
    class login {

        @BeforeEach
        void setUp() throws Exception {
            mvc.perform(post("/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getSignupRequestDto())));
        }

        @Test
        public void 성공() throws Exception {
            // given
            // when
            ResultActions resultActions = mvc.perform(post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getLoginRequestDto())))
                    .andDo(print());
            // then
            resultActions.andExpect(status().isOk());
        }

        @Test
        public void 아이디없는경우() throws Exception {
            // given
            email = "notexists@naver.com";
            // when
            ResultActions resultActions = mvc.perform(post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getLoginRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string(AGAIN_ID_CHECK));
        }

        @Test
        public void 비밀번호틀린경우() throws Exception {
            // given
            password = "12341234";
            // when
            ResultActions resultActions = mvc.perform(post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getLoginRequestDto())))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string(AGAIN_PASSWORD_CHECK));
        }

    }

    @Nested
    @DisplayName("로그아웃")
    class logout {
        MockHttpSession mockHttpSession = new MockHttpSession();

        @BeforeEach
        void setUp() throws Exception{
            mvc.perform(post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(getLoginRequestDto())));

            mockHttpSession.setAttribute(SessionConstants.SESSION_ID, email);
            mockHttpSession.setAttribute(SessionConstants.AUTH_TYPE, UserType.USER);
        }

        @Test
        public void 성공() throws Exception {
            // given
            // when
            ResultActions resultActions = mvc.perform(delete("/users/logout")
                            .session(mockHttpSession))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().isOk());
            Assertions.assertNull(mockHttpSession.getAttribute(SessionConstants.SESSION_ID));
            Assertions.assertNull(mockHttpSession.getAttribute(SessionConstants.AUTH_TYPE));
        }

    }


}
