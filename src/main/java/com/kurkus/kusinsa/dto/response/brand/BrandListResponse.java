package com.kurkus.kusinsa.dto.response.brand;


import java.util.List;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.entity.Brand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandListResponse {

    private List<BrandResponse> brandList;

    private BrandListResponse(List<BrandResponse> list){
        this.brandList = list;
    }

    public static BrandListResponse from(List<Brand> list){
        return new BrandListResponse(list.stream().map(b -> BrandResponse.from(b)).collect(Collectors.toList()));
    }

}
