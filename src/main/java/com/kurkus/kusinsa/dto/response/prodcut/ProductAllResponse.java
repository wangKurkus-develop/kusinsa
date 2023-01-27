package com.kurkus.kusinsa.dto.response.prodcut;

import com.kurkus.kusinsa.dto.response.brand.BrandResponse;
import com.kurkus.kusinsa.dto.response.category.CategoryResponse;
import com.kurkus.kusinsa.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductAllResponse {

    private ProductResponse productResponse;
    private CategoryResponse categoryResponse;
    private BrandResponse brandResponse;

    public ProductAllResponse(Product product){
        productResponse = ProductResponse.from(product);
        categoryResponse = CategoryResponse.from(product.getCategory());
        brandResponse = BrandResponse.from(product.getBrand());
    }


    public static ProductAllResponse from(Product product){
        return new ProductAllResponse(product);
    }


}
