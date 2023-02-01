package com.kurkus.kusinsa.entity.order;

import javax.persistence.*;

import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;


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

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    private boolean deleted;

    // 총 사용포인트 (포인트는 상품에관련없이 사용가능)
    @Column(name = "total_used_point", nullable = false)
    private long totalUsedPoint;
    // 총 적립 포인트
    @Column(name = "total_obtain_point", nullable = false)
    private long totalObtainPoint;

    public void delete(){
        this.deleted = true;
    }
}
