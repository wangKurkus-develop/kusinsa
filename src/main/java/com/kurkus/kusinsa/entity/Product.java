package com.kurkus.kusinsa.entity;

import javax.persistence.*;

import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import com.kurkus.kusinsa.enums.ProductType;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String content;

    private Long steams;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(nullable = false)
    private String image;

    private ProductType status;


}
