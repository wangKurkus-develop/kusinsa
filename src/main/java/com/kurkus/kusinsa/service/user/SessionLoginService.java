package com.kurkus.kusinsa.service.user;

import javax.servlet.http.HttpSession;

import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.utils.constants.SessionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionLoginService {

    private final HttpSession httpSession;

    public void login(User user) {
        httpSession.setAttribute(SessionConstants.SESSION_ID, user.getId());
        httpSession.setAttribute(SessionConstants.AUTH_TYPE, user.getType());
    }

    public Long getSessionUserId() {
        return (Long) httpSession.getAttribute(SessionConstants.SESSION_ID);
    }

    public UserType getUserType() {
        return (UserType) httpSession.getAttribute(SessionConstants.AUTH_TYPE);
    }

    public void logout() {
        httpSession.removeAttribute(SessionConstants.SESSION_ID);
        httpSession.removeAttribute(SessionConstants.AUTH_TYPE);
    }
}
