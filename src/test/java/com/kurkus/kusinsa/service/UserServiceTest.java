package com.kurkus.kusinsa.service;

import java.util.Optional;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.kurkus.kusinsa.dto.user.LoginRequestDto;
import com.kurkus.kusinsa.dto.user.SignupRequestDto;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.exception.UserException;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.utils.PasswordEncoder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    SessionLoginService sessionLoginService;

    @BeforeAll()
    public static void setUp(){
        mockStatic(PasswordEncoder.class);
    }

    private String email = "hello@naver.com";
    private String password = "1234";
    private String name = "최왕규";
    private String address = "쿠르쿠스시 쿠르쿠르아파트";
    private String phoneNumber = "010-1111-1111";



    private SignupRequestDto getSignupRequestDto(){
        return SignupRequestDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }

    private LoginRequestDto getLoginRequestDto(){
        return new LoginRequestDto(email, password);
    }

    @Nested
    @DisplayName("회원가입")
    class signup{

        private String stubEmail = "hello@naver.com";

        @Test
        @DisplayName("성공")
        public void success() throws Exception {
            // given
            given(userRepository.findByEmail(stubEmail)).willReturn(Optional.empty());
            // when
            userService.signup(getSignupRequestDto());
            // then
            then(userRepository).should(times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("실패-이미존재하는경우")
        public void fail() throws Exception {
            // given
            given(userRepository.findByEmail(stubEmail)).willReturn(Optional.of(User.builder().build()));
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
        public void 성공() throws Exception {
            // given
            given(userRepository.findByEmail(email)).willReturn(Optional.of(User.builder().password(password).build()));
            given(PasswordEncoder.matches(anyString(), anyString())).willReturn(true);
            // when
            userService.login(getLoginRequestDto());
            // then
            then(sessionLoginService).should(times(1)).login(any(User.class));
        }

        @Test
        public void 아이디_존재x() throws Exception {
            // given
            given(userRepository.findByEmail(email)).willReturn(Optional.empty());
            // when
            UserException ex = assertThrows(UserException.class, () -> userService.login(getLoginRequestDto()));
            // then
            assertEquals(AGAIN_ID_CHECK, ex.getMessage());
        }

        @Test
        public void 비빌번호_일치x() throws Exception {
            // given
            given(userRepository.findByEmail(email)).willReturn(Optional.of(User.builder().password(password).build()));
            given(PasswordEncoder.matches(anyString(), anyString())).willReturn(false);
            // when
            UserException ex = assertThrows(UserException.class, () -> userService.login(getLoginRequestDto()));
            // then
            assertEquals(AGAIN_PASSWORD_CHECK, ex.getMessage());
        }

    }
}