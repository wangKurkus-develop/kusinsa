package com.kurkus.kusinsa.integration;


import javax.transaction.Transactional;

import java.io.File;
import java.time.Duration;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.dto.request.user.DeviceCreateRequest;
import com.kurkus.kusinsa.dto.request.user.LoginRequest;
import com.kurkus.kusinsa.dto.request.user.SignupRequest;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//@ContextConfiguration(initializers = {DockerComposeInitializer.class})
//@Testcontainers
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class UserIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

//    @Container
//    static DockerComposeContainer composeContainer =
//            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
//                    .withExposedService("redis-session", 6379,
//                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));


    private Long id = 18L;
    private String email = "cwg@naver.com";
    private String password = "1234";
    private String name = "최왕규";
    private String address = "쿠르쿠스시 쿠르쿠르아파트";
    private String phoneNumber = "010-1111-1111";

    private SignupRequest getSignupRequestDto() {
        return SignupRequest.builder()
                .email(email)
                .password(password)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }

    private LoginRequest getLoginRequestDto() {
        return new LoginRequest(email, password);
    }


    @Nested
    @DisplayName("회원가입")
    class signup {
        @Test
        public void 성공() throws Exception {
            // given
            email = "testemail@naver.com";
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
                    .andExpect(content().string(NOT_FOUND_USER));
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
        @Test
        public void 성공() throws Exception {
            // given
            MockHttpSession mockHttpSession = new MockHttpSession();
            mockHttpSession.setAttribute(SESSION_ID, id);
            mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
            // when
            ResultActions resultActions = mvc.perform(delete("/users/logout")
                            .session(mockHttpSession))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().isOk());
            Assertions.assertNull(mockHttpSession.getAttribute(SESSION_ID));
            Assertions.assertNull(mockHttpSession.getAttribute(AUTH_TYPE));
        }

        @Test
        public void 실패_세션없는경우() throws Exception {
            // given
            // when
            ResultActions resultActions = mvc.perform(delete("/users/logout"))
                    .andDo(print());
            // then
            resultActions
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string(NOT_FOUND_SESSION));
        }
    }

    @Test
    public void deviceSave_유저_null인경우() throws Exception {
        // given
        DeviceCreateRequest request = new DeviceCreateRequest("testDeviceToken");
        // when
        ResultActions result = mvc.perform(post("/users" + "/devices").
                content(objectMapper.writeValueAsString(request)).
                contentType(MediaType.APPLICATION_JSON)).andDo(print());
        // then
        result.andExpect(status().isCreated());
    }

    @Test
    public void deviceSave_유저_null아닌경우() throws Exception {
        // given
        DeviceCreateRequest request = new DeviceCreateRequest(18L,"testDeviceToken");
        // when
        ResultActions result = mvc.perform(post("/users" + "/devices").
                content(objectMapper.writeValueAsString(request)).
                contentType(MediaType.APPLICATION_JSON)).andDo(print());
        // then
        result.andExpect(status().isCreated());
    }


}
