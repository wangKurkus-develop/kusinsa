package com.kurkus.kusinsa.dto.response.prodcut;

import com.kurkus.kusinsa.dto.response.brand.BrandResponse;
import com.kurkus.kusinsa.dto.response.category.CategoryResponse;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductAllResponse {

    private ProductResponse productResponse;
    private CategoryResponse categoryResponse;
    private BrandResponse brandResponse;

    public ProductAllResponse(Product product){
        productResponse = ProductResponse.of(product);
        categoryResponse = CategoryResponse.of(product.getCategory());
        brandResponse = BrandResponse.of(product.getBrand());
    }


    public static ProductAllResponse of(Product product){
        return new ProductAllResponse(product);
    }


}
