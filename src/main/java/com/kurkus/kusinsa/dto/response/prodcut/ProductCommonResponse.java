package com.kurkus.kusinsa.dto.response.prodcut;


import com.kurkus.kusinsa.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCommonResponse {

    private Long id;
    private String name;
    private Long price;
    private String thumbnailImagePath;
    // brand
    private Long brandId;
    private String brandName;

    public static ProductCommonResponse of(Product product){
        return new ProductCommonResponse(product.getId(), product.getName(), product.getPrice(),
                product.getThumbnailImagePath(), product.getBrand().getId(), product.getBrand().getName());
    }

}
