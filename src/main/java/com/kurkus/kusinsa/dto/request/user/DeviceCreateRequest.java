package com.kurkus.kusinsa.dto.request.user;


import com.kurkus.kusinsa.entity.Device;
import com.kurkus.kusinsa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class DeviceCreateRequest {

    private Long userId;
    private String deviceToken;

    public DeviceCreateRequest(Long userId, String deviceToken) {
        this.userId = userId;
        this.deviceToken = deviceToken;
    }

    public DeviceCreateRequest(String deviceToken){
        this.deviceToken = deviceToken;
    }

    public Device toDevice(User user) {
        return Device.builder()
                .user(user)
                .deviceToken(deviceToken)
                .build();
    }

    public Device toNotUserDevice() {
        return Device.builder()
                .deviceToken(deviceToken)
                .build();
    }

}
