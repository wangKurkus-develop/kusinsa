package com.kurkus.kusinsa.dto.response.prodcut;


import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.enums.ProductType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    private Long productId;
    private String productName;
    private Long price;
    private String content;
    private ProductType status;
    private String originImagePath;
    private String thumbnailImagePath;
    private long stock;
    private long likes;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;


    public static ProductResponse from(Product product){
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .content(product.getContent())
                .likes(product.getLikes())
                .status(product.getStatus())
                .originImagePath(product.getOriginImagePath())
                .thumbnailImagePath(product.getThumbnailImagePath())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .brandId(product.getBrand().getId())
                .brandName(product.getBrand().getName())
                .build();
    }
}
