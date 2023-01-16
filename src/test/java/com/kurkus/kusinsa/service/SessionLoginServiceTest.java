package com.kurkus.kusinsa.service;


import javax.servlet.http.HttpSession;

import static com.kurkus.kusinsa.utils.constants.SessionConstants.*;
import static org.mockito.BDDMockito.*;

import com.kurkus.kusinsa.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class SessionLoginServiceTest {

    @Mock
    HttpSession httpSession;

    @InjectMocks
    SessionLoginService sessionLoginService;

    private Long userId = 1L;
    private UserType userType = UserType.USER;

    @Test
    void getSessionUserId() {
        // given
        given(httpSession.getAttribute(SESSION_ID)).willReturn(userId);
        // when
        sessionLoginService.getSessionUserId();
        // then
        then(httpSession).should(times(1)).getAttribute(anyString());
    }

    @Test
    void getUserType() {
        // given
        given(httpSession.getAttribute(AUTH_TYPE)).willReturn(userType);
        // when
        sessionLoginService.getUserType();
        // then
        then(httpSession).should(times(1)).getAttribute(anyString());
    }

    @Test
    void logout() {
        // given
        // when
        sessionLoginService.logout();
        // then
        then(httpSession).should(times(2)).removeAttribute(anyString());
    }
}