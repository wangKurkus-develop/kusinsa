package com.kurkus.kusinsa.common;

import static com.kurkus.kusinsa.utils.constants.SessionConstants.AUTH_TYPE;
import static com.kurkus.kusinsa.utils.constants.SessionConstants.SESSION_ID;

import com.kurkus.kusinsa.enums.UserType;
import org.springframework.mock.web.MockHttpSession;

public class SessionLogin {

    
    public static void adminLogin(MockHttpSession mockHttpSession){
        mockHttpSession.setAttribute(SESSION_ID, 18L);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.ADMIN);
    }

    public static void userLogin(MockHttpSession mockHttpSession){
        mockHttpSession.setAttribute(SESSION_ID, 20L);
        mockHttpSession.setAttribute(AUTH_TYPE, UserType.USER);
    }
}
