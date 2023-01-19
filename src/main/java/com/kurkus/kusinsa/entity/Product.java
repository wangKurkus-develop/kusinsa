package com.kurkus.kusinsa.entity;

import javax.persistence.*;

import com.kurkus.kusinsa.dto.request.product.ProductUpdateRequest;
import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import com.kurkus.kusinsa.enums.ProductType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@DynamicInsert
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

    private long steams;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private ProductType status;


    @Column(name = "origin_image_path",columnDefinition = "LONGTEXT")
    private String originImagePath;

    @Column(name = "thumbnail_image_path",columnDefinition = "LONGTEXT")
    private String thumbnailImagePath;

    private long stock;

    @Column(nullable = false)
    private boolean deleted;


    public void update(ProductUpdateRequest request) {
        this.name = request.getName();
        this.price = request.getPrice();
        this.content  = request.getContent();
        this.stock = request.getStock();
        this.status = request.getStatus();
    }

    public void delete(){
        this.deleted = true;
    }

    public void decrease(int quantity){
        this.stock = this.stock - quantity;
        if(this.stock == 0){
            this.status = ProductType.SOLD_OUT;
        }
    }

}
