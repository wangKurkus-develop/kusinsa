package com.kurkus.kusinsa.entity;

import javax.persistence.*;

import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import lombok.*;

@Table(name = "stock_history")
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class StockHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stock;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
