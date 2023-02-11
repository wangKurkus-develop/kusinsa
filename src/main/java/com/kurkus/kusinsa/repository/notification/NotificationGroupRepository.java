package com.kurkus.kusinsa.repository.notification;

import java.util.Optional;

import com.kurkus.kusinsa.entity.notification.NotificationGroup;
import com.kurkus.kusinsa.enums.notification.NotificationGroupStatus;
import com.kurkus.kusinsa.exception.notification.NotificationGroupNotFoundException;
import com.kurkus.kusinsa.exception.user.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationGroupRepository extends JpaRepository<NotificationGroup, Long> {

    @Query(value = "select g from NotificationGroup g where g.product.id= :productId and g.status = :status and g.deleted = false ")
    Optional<NotificationGroup> findByProductIdAndStatus(@Param("productId") Long productId,
                                                         @Param("status")NotificationGroupStatus status);

    default NotificationGroup getByProductIdAndStatus(Long productId, NotificationGroupStatus status){
        return findByProductIdAndStatus(productId, status).orElseThrow(()-> new NotificationGroupNotFoundException());
    }

}
