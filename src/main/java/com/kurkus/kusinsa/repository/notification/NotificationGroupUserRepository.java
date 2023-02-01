package com.kurkus.kusinsa.repository.notification;

import java.util.List;

import com.kurkus.kusinsa.entity.notification.NotificationUser;
import com.kurkus.kusinsa.enums.notification.NotificationGroupStatus;
import com.kurkus.kusinsa.enums.notification.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationGroupUserRepository extends JpaRepository<NotificationUser, Long> {

    @Query(value = "select u from NotificationUser u where u.deleted = false and u.status = :status")
    List<NotificationUser> findAllByGroupId(@Param("groupId") Long groupId, @Param("status") NotificationStatus status);

}
