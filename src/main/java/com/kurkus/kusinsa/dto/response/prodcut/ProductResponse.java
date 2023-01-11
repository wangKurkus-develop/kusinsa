package com.kurkus.kusinsa.dto.response.prodcut;


import java.time.LocalDateTime;

import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.enums.ProductType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    private Long id;
    private String name;
    private Long price;
    private String content;
    private long steams;
    private ProductType status;
    private String originImagePath;
    private String thumbnailImagePath;
    private long stock;


    public static ProductResponse of(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .content(product.getContent())
                .steams(product.getSteams())
                .status(product.getStatus())
                .originImagePath(product.getOriginImagePath())
                .thumbnailImagePath(product.getThumbnailImagePath())
                .stock(product.getStock())
                .build();
    }
}
