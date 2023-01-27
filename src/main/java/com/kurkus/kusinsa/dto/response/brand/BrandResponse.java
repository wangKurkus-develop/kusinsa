package com.kurkus.kusinsa.dto.response.brand;

import com.kurkus.kusinsa.entity.Brand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandResponse {

    private Long id;
    private String name;

    private BrandResponse(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static BrandResponse from(Brand brand){
        return new BrandResponse(brand.getId(), brand.getName());
    }
}
