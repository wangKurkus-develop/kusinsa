package com.kurkus.kusinsa.repository.notification;

import java.util.List;

import com.kurkus.kusinsa.entity.notification.NotificationUser;
import com.kurkus.kusinsa.enums.notification.NotificationGroupStatus;
import com.kurkus.kusinsa.enums.notification.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationGroupUserRepository extends JpaRepository<NotificationUser, Long> {

    //    @Query(value = "select u.email from notification_group_user n inner join User u on u.id = n.user_id " +
//            "where n.notification_group_id = :groupId and u.deleted= false"
//            , nativeQuery = true)
//    List<NotificationUser> findGroupUserEmail(@Param("groupId") Long groupId);
    @Query(value = "select n from NotificationUser n join fetch n.user u " +
            "where n.notificationGroup.id = :groupId and n.status = 'RECRUIT' and n.deleted= false and n.status = 'WAIT' ")
    List<NotificationUser> findGroupUserEmail(@Param("groupId") Long groupId);


    @Modifying
    @Query(value = "update NotificationUser n set n.status = 'COMPLETE' where n.notificationGroup.id = :groupId and n.deleted = false")
    int updateStatusComplete(@Param("groupId") Long groupId);


    boolean existsByUserIdAndNotificationGroupId(Long userId, Long notificationGroupId);

}
