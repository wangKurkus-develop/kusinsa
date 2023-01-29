package com.kurkus.kusinsa.service.user;


import com.kurkus.kusinsa.dto.request.user.DeviceCreateRequest;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.repository.DeviceRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;


    // 외래키 제약조건이있어서 0이나 이런값으로 대체하는거슨 불가능하다
    public void save(DeviceCreateRequest request) {
        if(request.getUserId() == null){
            deviceRepository.save(request.toNotUserDevice());
        } else{
            User user = userRepository.getById(request.getUserId());
            deviceRepository.save(request.toDevice(user));
        }
    }
}
