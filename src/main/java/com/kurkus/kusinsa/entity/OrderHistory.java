package com.kurkus.kusinsa.entity;

import javax.persistence.*;

import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import com.kurkus.kusinsa.enums.DeliveryStatus;
import com.kurkus.kusinsa.enums.OrderStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "orders_product_history")
public class OrderHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "obtain_point")
    private long obtainPoint;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private boolean deleted;

    public void delete(){
        this.deleted = true;
    }

    public void refund(){
        this.orderStatus = OrderStatus.REFUND;
    }




}
