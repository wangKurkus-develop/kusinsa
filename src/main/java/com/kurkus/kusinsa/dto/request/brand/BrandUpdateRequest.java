package com.kurkus.kusinsa.dto.request.brand;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandUpdateRequest {

    private String brandName;

    public BrandUpdateRequest(String brandName){
        this.brandName = brandName;
    }
}
