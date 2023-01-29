package com.kurkus.kusinsa.service.user;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.kurkus.kusinsa.dto.request.user.LoginRequest;
import com.kurkus.kusinsa.dto.request.user.SignupRequest;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.exception.user.UserException;
import com.kurkus.kusinsa.exception.user.UserNotFoundException;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.service.user.SessionLoginService;
import com.kurkus.kusinsa.service.user.UserService;
import com.kurkus.kusinsa.utils.PasswordEncoder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    SessionLoginService sessionLoginService;

    @Mock
    ApplicationEventPublisher publisher;

    @BeforeAll()
    public static void setUp(){
        mockStatic(PasswordEncoder.class);
    }

    private Long id = 10L;
    private String email = "hello@naver.com";
    private String password = "1234";
    private String name = "최왕규";
    private String address = "쿠르쿠스시 쿠르쿠르아파트";
    private String phoneNumber = "010-1111-1111";



    private SignupRequest getSignupRequestDto(){
        return SignupRequest.builder()
                .email(email)
                .password(password)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }

    private LoginRequest getLoginRequestDto(){
        return new LoginRequest(email, password);
    }

    @Nested
    @DisplayName("회원가입")
    class signup{

        private String stubEmail = "hello@naver.com";

        @Test
        @DisplayName("성공")
        public void success() throws Exception {
            // given
            given(userRepository.existsByEmail(stubEmail)).willReturn(false);
            // when
            userService.signup(getSignupRequestDto());
            // then
            then(userRepository).should(times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("실패-이미존재하는경우")
        public void fail() throws Exception {
            // given
            given(userRepository.existsByEmail(stubEmail)).willReturn(true);
            // when
            UserException ex = assertThrows(UserException.class, () -> {
                userService.signup(getSignupRequestDto());
            });
            // then
            assertEquals(EXISTS_EMAIL, ex.getMessage());
        }
    }



    @Nested
    @DisplayName("로그인")
    class login{

        @Test
        public void 성공() {
            // given
            given(userRepository.getByEmail(email)).willReturn(User.builder().id(id).email(email).password(password).build());
            given(PasswordEncoder.matches(anyString(), anyString())).willReturn(true);
            // when
            userService.login(getLoginRequestDto());
            // then
            then(sessionLoginService).should(times(1)).login(any(User.class));
        }

        @Test
        public void 아이디_존재x() throws Exception {
            // given
            given(userRepository.getByEmail(email)).willThrow(new UserNotFoundException());
            // when
            UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> userService.login(getLoginRequestDto()));
            // then
            assertEquals(NOT_FOUND_USER, ex.getMessage());
        }

        @Test
        public void 비빌번호_일치x() throws Exception {
            // given
            given(userRepository.getByEmail(email)).willReturn(User.builder().password(password).build());
            given(PasswordEncoder.matches(anyString(), anyString())).willReturn(false);
            // when
            UserException ex = assertThrows(UserException.class, () -> userService.login(getLoginRequestDto()));
            // then
            assertEquals(AGAIN_PASSWORD_CHECK, ex.getMessage());
        }

    }
}