package com.kurkus.kusinsa.service.point;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.entity.Point;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.exception.user.UserNotFoundException;
import com.kurkus.kusinsa.repository.PointRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PointServiceImplTest {

    @InjectMocks
    PointServiceImpl pointService;

    @Mock
    UserRepository userRepository;

    @Mock
    PointRepository pointRepository;


    public void save(Long userId, PointCreateRequest request) {
        User user = userRepository.getById(userId);
        pointRepository.save(request.toPoint(user));
    }

    @Nested
    class save {
        Long userId = 20L;
        PointCreateRequest request = new PointCreateRequest(100L, "내용1", PointType.OBTAIN);

        @Test
        public void save() {
            // given

            given(userRepository.getById(anyLong())).willReturn(User.builder().build());
            // when
            pointService.save(userId, request);
            // then
            then(pointRepository).should(times(1)).save(any(Point.class));
        }

        @Test
        public void 실패_NotFoundUser() {
            // given
            given(userRepository.getById(anyLong())).willThrow(new UserNotFoundException());
            // when
            UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> pointService.save(userId, request));
            // then
            assertEquals(NOT_FOUND_USER, ex.getMessage());
        }
    }



}