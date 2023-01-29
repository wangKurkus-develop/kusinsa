package com.kurkus.kusinsa.dto.request.user;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeviceRequest {

    private Long userId;
    private String deviceToken;

}
