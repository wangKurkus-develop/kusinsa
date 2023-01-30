package com.kurkus.kusinsa.dto.request.notification;


import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.entity.notification.NotificationGroup;
import com.kurkus.kusinsa.entity.notification.NotificationUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationCreateRequest {

    private Long productId;

    public NotificationCreateRequest(Long productId){
        this.productId = productId;
    }

    public NotificationGroup toNotificationGroup(Product product, String uniqueKey){
        return NotificationGroup.builder()
                .product(product)
                .uniqueKey(uniqueKey)
                .build();
    }

    public NotificationUser toNotificationUser(User user, NotificationGroup group){
        return NotificationUser.builder()
                .user(user)
                .notificationGroup(group)
                .build();
    }
}
