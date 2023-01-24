package com.kurkus.kusinsa.dto.response.prodcut;


import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.enums.ProductType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRecentResponse {

    private Long id;
    private String name;
    private Long price;
    private String content;
    private String thumbnailImagePath;
    // brand
    private Long brandId;
    private String brandName;

    public static ProductRecentResponse of(Product product){
        return new ProductRecentResponse(product.getId(), product.getName(), product.getPrice(), product.getContent(),
                product.getThumbnailImagePath(), product.getBrand().getId(), product.getBrand().getName());
    }
}
