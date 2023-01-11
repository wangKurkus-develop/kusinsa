package com.kurkus.kusinsa.dto.request.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

}
