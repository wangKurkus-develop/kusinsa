package com.kurkus.kusinsa.service.notification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import static com.kurkus.kusinsa.enums.notification.NotificationGroupStatus.*;
import static com.kurkus.kusinsa.enums.notification.NotificationStatus.*;

import com.kurkus.kusinsa.dao.NotificationDao;
import com.kurkus.kusinsa.dto.request.notification.NotificationCreateRequest;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.entity.documents.NotificationMessage;
import com.kurkus.kusinsa.entity.notification.NotificationGroup;
import com.kurkus.kusinsa.entity.notification.NotificationUser;
import com.kurkus.kusinsa.entity.order.OrderHistory;
import com.kurkus.kusinsa.enums.notification.NotificationGroupStatus;
import com.kurkus.kusinsa.enums.notification.NotificationStatus;
import com.kurkus.kusinsa.exception.notification.NotificationGroupNotFoundException;
import com.kurkus.kusinsa.repository.OrderHistoryRepository;
import com.kurkus.kusinsa.repository.mongo.NotificationMessageRepository;
import com.kurkus.kusinsa.repository.product.ProductRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupUserRepository;
import com.kurkus.kusinsa.service.notification.email.EmailService;
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
    private final OrderHistoryRepository orderHistoryRepository;

    private final EmailService emailService;
    private final NotificationMessageRepository notifyMessageRepo;

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
            Optional<NotificationGroup> group = groupRepository.findByProductIdAndStatus(request.getProductId(), RECRUIT);
            if (!group.isPresent()) {
                throw new NotificationGroupNotFoundException();
            }
            groupUserRepository.save(request.toNotificationUser(user, group.get()));
        }
    }

    @Transactional
    public void notifyDelivery(Long orderHistoryId){
        OrderHistory orderHistory = orderHistoryRepository.findByIdWithUserAndProduct(orderHistoryId);
        User user = orderHistory.getUser();
        Product product = orderHistory.getProduct();
        String msg = emailService.sendDeliveryComplete(user.getEmail(), product.getName());
        notifyMessageRepo.save(new NotificationMessage(user.getId(), msg));
    }


    /**
     * 1. 이메일 보내기
     * 2. 메시지 저장
     * 3. 그룹 메시지 완료상태변경
     * 4. 그룹의 인원들 메시지 완료상태 변경 (배치)
     * V2 : 실패의 경우에 대한 예외처리
     */
    @Transactional
    public void notifyGroup(Long productId, String productName){
        NotificationGroup group = groupRepository.getByProductIdAndStatus(productId, RECRUIT);
        List<NotificationUser> users = groupUserRepository.findGroupUserEmail(group.getId());
        String message =  emailService.sendGroup(getEmailList(users), productName);
        saveMessage(users, message);
        group.updateComplete();
        groupUserRepository.updateStatusComplete(group.getId());
    }

    private String[] getEmailList(List<NotificationUser> list){
        String[] emails = new String[list.size()];
        for(int i=0; i<list.size(); i++){
            emails[i] = list.get(i).getUser().getEmail();
        }
        return emails;
    }

    private void saveMessage(List<NotificationUser> users,  String message){
        List<NotificationMessage> list = users.stream()
                .map(l -> new NotificationMessage(l.getUser().getId(), message))
                .collect(Collectors.toList());
        notifyMessageRepo.saveAll(list);
    }




}
