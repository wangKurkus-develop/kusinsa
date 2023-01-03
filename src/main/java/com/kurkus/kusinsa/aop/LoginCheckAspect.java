package com.kurkus.kusinsa.aop;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.exception.SessionLoginException;
import com.kurkus.kusinsa.service.SessionLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final SessionLoginService sessionLoginService;

    @Before("@annotation(com.kurkus.kusinsa.annotation.LoginCheck) && @annotation(target)")
    public void sessionLoginCheck(LoginCheck target) {
        String sessionUserId = sessionLoginService.getSessionUserId();

        if (sessionUserId == null) {
            throw new SessionLoginException("세션 아이디가 존재하지않습니다", HttpStatus.UNAUTHORIZED);
        }

        UserType targetUserType = target.userType();

        if (targetUserType == UserType.ALL) {
            return;
        }

        UserType userType = sessionLoginService.getUserType();

        if (userType != targetUserType) {
            throw new SessionLoginException("권한이 없습니다", HttpStatus.FORBIDDEN);
        }
    }


}
