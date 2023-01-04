package com.kurkus.kusinsa.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDto {

    private String email;
    private String password;

    public LoginRequestDto(String email, String password){
        this.email = email;
        this.password = password;
    }

}
