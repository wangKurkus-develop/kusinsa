package com.kurkus.kusinsa.dto.user;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequestDto {

    private String email;
    private String password;

}
