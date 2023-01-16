package com.kurkus.kusinsa.entity;

import javax.persistence.*;

import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import com.kurkus.kusinsa.enums.OrderType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "orders")
@DynamicInsert
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_price", nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderType status;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "order_history", nullable = false, columnDefinition = "LONGTEXT")
    private String history;

    @Column(nullable = false)
    private boolean deleted;

    public void delete(){
        this.deleted = true;
    }
}
