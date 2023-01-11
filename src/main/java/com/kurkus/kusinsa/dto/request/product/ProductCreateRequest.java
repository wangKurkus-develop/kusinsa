package com.kurkus.kusinsa.dto.request.product;

import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.entity.Product;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProductCreateRequest {
    private String name;
    private Long price;
    private String content;
    private Long categoryId;
    private Long brandId;
    private String originImagePath;
    private String thumbnailImagePath;


    public Product toProduct(Category category, Brand brand){
        return Product.builder()
                .name(name)
                .price(price)
                .content(content)
                .category(category)
                .brand(brand)
                .originImagePath(originImagePath)
                .thumbnailImagePath(thumbnailImagePath)
                .build();
    }



}
