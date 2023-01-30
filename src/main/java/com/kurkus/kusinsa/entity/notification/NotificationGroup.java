package com.kurkus.kusinsa.entity.notification;

import javax.persistence.*;

import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import com.kurkus.kusinsa.enums.notification.NotificationGroupStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "notification_group")
@DynamicInsert
@AllArgsConstructor
@Builder
public class NotificationGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NotificationGroupStatus status;

    @Column(nullable = false)
    private boolean deleted;

    @Column(name = "unique_key")
    private String uniqueKey;



}
