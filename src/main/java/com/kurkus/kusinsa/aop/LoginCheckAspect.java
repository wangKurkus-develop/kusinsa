package com.kurkus.kusinsa.aop;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

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
        Long sessionUserId = sessionLoginService.getSessionUserId();

        if (sessionUserId == null) {
            throw new SessionLoginException(NOT_FOUND_SESSION, HttpStatus.UNAUTHORIZED);
        }

        UserType targetUserType = target.userType();

        if (targetUserType == UserType.ALL) {
            return;
        }

        UserType userType = sessionLoginService.getUserType();

        if (userType != targetUserType) {
            throw new SessionLoginException(FORBIDDEN, HttpStatus.FORBIDDEN);
        }
    }


}
