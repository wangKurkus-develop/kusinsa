package com.kurkus.kusinsa.service.notification;

import java.util.Optional;

import com.kurkus.kusinsa.dao.NotificationDao;
import com.kurkus.kusinsa.dto.request.notification.NotificationCreateRequest;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.entity.notification.NotificationGroup;
import com.kurkus.kusinsa.enums.notification.NotificationGroupStatus;
import com.kurkus.kusinsa.exception.notification.NotificationGroupNotFoundException;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ProductRepository productRepository;
    private final NotificationGroupRepository groupRepository;
    private final NotificationGroupUserRepository groupUserRepository;
    private final NotificationDao notificationDao;
    private final UserRepository userRepository;

    @Transactional
    public void save(Long userId, NotificationCreateRequest request) {
        boolean isUniqueKey = notificationDao.createUniqueKey(request.getProductId());
        User user = userRepository.getById(userId);
        if (isUniqueKey == true) {
            NotificationGroup group = groupRepository.save(request.toNotificationGroup(
                    productRepository.getById(request.getProductId()),
                    notificationDao.getUniqueKey(request.getProductId())));
            groupUserRepository.save(request.toNotificationUser(user, group));
        } else {
            Optional<NotificationGroup> group = groupRepository.findByProductIdAndStatus(request.getProductId(), NotificationGroupStatus.RECRUIT);
            if (!group.isPresent()) {
                throw new NotificationGroupNotFoundException();
            }
            groupUserRepository.save(request.toNotificationUser(user, group.get()));
        }
    }


}
