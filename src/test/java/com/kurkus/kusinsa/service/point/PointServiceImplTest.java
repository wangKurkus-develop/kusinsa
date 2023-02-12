package com.kurkus.kusinsa.service.point;

import java.util.ArrayList;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dao.PointDao;
import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
import com.kurkus.kusinsa.entity.Point;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.events.point.PointLoginSavedEvent;
import com.kurkus.kusinsa.events.point.PointOrderSavedEvent;
import com.kurkus.kusinsa.exception.user.UserNotFoundException;
import com.kurkus.kusinsa.repository.point.PointRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PointServiceImplTest {

    @InjectMocks
    PointServiceImpl pointService;

    @Mock
    UserRepository userRepository;

    @Mock
    PointRepository pointRepository;

    @Mock
    PointDao pointDao;


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

    @Test
    public void loginPointSave() throws Exception {
        // given
        given(pointDao.checkTodayLoginPoint(anyLong())).willReturn(1L);
        given(userRepository.getById(anyLong())).willReturn(any(User.class));
        // when
        pointService.loginPointSave(new PointLoginSavedEvent(1L));
        // then
        then(pointRepository).should(times(1)).save(any(Point.class));
    }

    @Test
    public void orderPointSave_사용포인트없는경우() throws Exception {
        // given
        given(userRepository.getById(anyLong())).willReturn(any(User.class));
        // when
        pointService.orderPointSave(new PointOrderSavedEvent(1L,100, 0, 1L));
        // then
        then(pointRepository).should(times(1)).save(any(Point.class));
    }

    @Test
    public void orderPointSave_사용포인트있는경우() throws Exception {
        // given
        given(userRepository.getById(anyLong())).willReturn(any(User.class));
        // when
        pointService.orderPointSave(new PointOrderSavedEvent(1L,100, 1000, 1L));
        // then
        then(pointRepository).should(times(2)).save(any(Point.class));
    }

    @Test
    public void findPointSum() throws Exception {
        // given
        given(pointRepository.findPointSum(anyLong())).willReturn(anyLong());
        // when
        pointService.findPointSum(1L);
        // then
        then(pointRepository).should(times(1)).findPointSum(anyLong());
    }

    @Test
    public void searchCondition() throws Exception {
        // given
        given(pointRepository.searchPageCondition(any(), any())).willReturn(new PageImpl(new ArrayList<Point>()));
        // when
        pointService.searchCondition(any(PointSearchCondition.class), anyInt());
        // then
        then(pointRepository).should(times(1)).searchPageCondition(any(), any());
    }


}