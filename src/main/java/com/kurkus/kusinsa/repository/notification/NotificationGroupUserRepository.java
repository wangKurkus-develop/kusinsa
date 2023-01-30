package com.kurkus.kusinsa.repository.notification;

import com.kurkus.kusinsa.entity.notification.NotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationGroupUserRepository extends JpaRepository<NotificationUser, Long> {
}
