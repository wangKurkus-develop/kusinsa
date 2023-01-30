package com.kurkus.kusinsa.entity.notification;


import javax.persistence.*;

import com.kurkus.kusinsa.dto.request.product.status.NotificationStatus;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "notification_group_user")
@DynamicInsert
@AllArgsConstructor
@Builder
public class NotificationUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_group_id")
    private NotificationGroup notificationGroup;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Column(nullable = false)
    private boolean deleted;
}
