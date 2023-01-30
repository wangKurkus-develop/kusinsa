package com.kurkus.kusinsa.service.user;

import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dto.request.user.DeviceCreateRequest;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.repository.DeviceRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @InjectMocks
    DeviceService deviceService;

    @Mock
    DeviceRepository deviceRepository;

    @Mock
    UserRepository userRepository;

    @Test
    void 유저가_NULL아닌경우() {
        // given
        DeviceCreateRequest request = new DeviceCreateRequest(14L, "test");
        given(userRepository.getById(any())).willReturn(User.builder().build());
        // when
        deviceService.save(request);
        // then
        then(userRepository).should(times(1)).getById(anyLong());
        then(deviceRepository).should(times(1)).save(any());
    }

    @Test
    public void 유저가_NULL인경우() {
        // given
        DeviceCreateRequest request = new DeviceCreateRequest("test");
        // when
        deviceService.save(request);
        // then
        then(deviceRepository).should(times(1)).save(any());
    }
}