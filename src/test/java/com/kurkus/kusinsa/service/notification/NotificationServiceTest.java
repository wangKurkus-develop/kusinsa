package com.kurkus.kusinsa.service.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.ErrorMessages.DUPLICATE_APPLY;
import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dao.NotificationDao;
import com.kurkus.kusinsa.dto.request.notification.NotificationCreateRequest;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.entity.documents.NotificationMessage;
import com.kurkus.kusinsa.entity.notification.NotificationGroup;
import com.kurkus.kusinsa.entity.notification.NotificationUser;
import com.kurkus.kusinsa.entity.order.OrderHistory;
import com.kurkus.kusinsa.enums.notification.NotificationGroupStatus;
import com.kurkus.kusinsa.exception.notification.NotificationGroupException;
import com.kurkus.kusinsa.exception.notification.NotificationGroupNotFoundException;
import com.kurkus.kusinsa.repository.OrderHistoryRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.repository.mongo.NotificationMessageRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupUserRepository;
import com.kurkus.kusinsa.repository.product.ProductRepository;
import com.kurkus.kusinsa.service.notification.email.EmailService;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService;

    @Mock
    ProductRepository productRepository;
    @Mock
    NotificationGroupRepository groupRepository;
    @Mock
    NotificationGroupUserRepository groupUserRepository;
    @Mock
    NotificationDao notificationDao;
    @Mock
    UserRepository userRepository;
    @Mock
    OrderHistoryRepository orderHistoryRepository;
    @Mock
    EmailService emailService;
    @Mock
    NotificationMessageRepository notifyMessageRepo;

    @Test
    void save_그룹처음생성() {
        Long userId = 1L;
        Long productId = 1L;
        NotificationCreateRequest request = new NotificationCreateRequest(productId);
        given(notificationDao.createUniqueKey(productId)).willReturn(true);
        given(userRepository.getById(anyLong())).willReturn(User.builder().build());
        given(productRepository.getById(anyLong())).willReturn(Product.builder().build());
        given(notificationDao.getUniqueKey(productId)).willReturn(anyString());

        notificationService.save(userId, request);
        then(groupRepository).should(times(1)).save(any(NotificationGroup.class));
        then(groupUserRepository).should(times(1)).save(any(NotificationUser.class));
    }

    @Test
    public void save_그룹생성안된경우() {
        Long userId = 1L;
        Long productId = 1L;
        NotificationCreateRequest request = new NotificationCreateRequest(productId);

        given(userRepository.getById(userId)).willReturn(User.builder().build());
        given(groupRepository.findByProductIdAndStatus(productId, NotificationGroupStatus.RECRUIT)).willReturn(Optional.empty());
        NotificationGroupNotFoundException ex = assertThrows(NotificationGroupNotFoundException.class,
                () -> notificationService.save(userId, request));
        assertEquals(ex.getMessage(), NOT_FOUND_NOTIFICATION_GROUP);
    }

    @Test
    public void save_그룹존재하는경우_이미생성된경우_중복신청() {
        Long userId = 1L;
        Long productId = 1L;
        Long groupId = 1L;
        NotificationCreateRequest request = new NotificationCreateRequest(productId);
        given(notificationDao.createUniqueKey(productId)).willReturn(false);
        given(userRepository.getById(anyLong())).willReturn(User.builder().build());

        given(groupRepository.findByProductIdAndStatus(productId, NotificationGroupStatus.RECRUIT))
                .willReturn(Optional.of(NotificationGroup.builder().id(groupId).build()));

        given(groupUserRepository.existsByUserIdAndNotificationGroupId(anyLong(), anyLong()))
                .willReturn(true);

        NotificationGroupException ex = assertThrows(NotificationGroupException.class, () -> notificationService.save(userId, request));
        assertEquals(ex.getMessage(), DUPLICATE_APPLY);
        then(groupRepository).should(times(1)).findByProductIdAndStatus(anyLong(), any());
    }

    @Test
    public void save_그룹존재하는경우_이미생성된경우_중복신청아닌경우(){
        Long userId = 1L;
        Long productId = 1L;
        NotificationCreateRequest request = new NotificationCreateRequest(productId);

        given(notificationDao.createUniqueKey(productId)).willReturn(false);
        given(userRepository.getById(userId)).willReturn(User.builder().build());
        given(groupRepository.findByProductIdAndStatus(productId, NotificationGroupStatus.RECRUIT))
                .willReturn(Optional.of(NotificationGroup.builder().id(anyLong()).build()));
        given(groupUserRepository.existsByUserIdAndNotificationGroupId(userId, anyLong())).willReturn(false);

        notificationService.save(userId, request);

        then(groupUserRepository).should(times(1)).save(any(NotificationUser.class));
    }

    @Test
    void notifyDelivery() {
        Long orderHistoryId = 1L;
        User user = User.builder().email("email").build();
        Product product = Product.builder().name("상품이름").build();
        OrderHistory orderHistory = OrderHistory.builder().product(product).user(user).build();
        given(orderHistoryRepository.findByIdWithUserAndProduct(orderHistoryId)).willReturn(orderHistory);
        given(emailService.sendDeliveryComplete(anyString(), anyString())).willReturn("메시지내용");

        notificationService.notifyDelivery(orderHistoryId);
        then(notifyMessageRepo).should(times(1)).save(any(NotificationMessage.class));
    }


    @Test
    void notifyGroup() {
        Long productId = 1L;
        String productName = "상품이름";
        Long groupId = 1L;
        given(groupRepository.getByProductIdAndStatus(productId,
                NotificationGroupStatus.RECRUIT)).willReturn(NotificationGroup.builder().id(groupId).build());
        given(groupUserRepository.findGroupUserEmail(anyLong())).willReturn(new ArrayList<>());
        given(emailService.sendGroup(any(), any())).willReturn("메시지내용");

        notificationService.notifyGroup(productId, productName);
        then(groupUserRepository).should(times(1)).updateStatusComplete(groupId);
    }
}