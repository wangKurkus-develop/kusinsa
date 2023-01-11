package com.kurkus.kusinsa.dto.request.brand;

import com.kurkus.kusinsa.entity.Brand;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BrandCreateRequest {

    private String brandName;

    public BrandCreateRequest(String brandName){
        this.brandName = brandName;
    }

    public Brand toBrand(){
        return Brand.builder()
                .name(brandName)
                .build();
    }
}
