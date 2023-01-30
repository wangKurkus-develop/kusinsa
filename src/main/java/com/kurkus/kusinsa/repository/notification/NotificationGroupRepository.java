package com.kurkus.kusinsa.repository.notification;

import java.util.Optional;

import com.kurkus.kusinsa.entity.notification.NotificationGroup;
import com.kurkus.kusinsa.enums.notification.NotificationGroupStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationGroupRepository extends JpaRepository<NotificationGroup, Long> {

    @Query(value = "select g from NotificationGroup g where g.product.id= :productId and g.status = :status")
    Optional<NotificationGroup> findByProductIdAndStatus(@Param("productId") Long productId,
                                                         @Param("status")NotificationGroupStatus status);


}
