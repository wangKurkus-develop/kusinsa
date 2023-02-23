package com.kurkus.kusinsa.dto.response.prodcut;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductWithRankResponse {

    private List<ProductCommonResponse> products;
    private List<Long> rank;

    public ProductWithRankResponse(List<ProductCommonResponse> products , List<Long> rank){
        this.products = products;
        this.rank = rank;
    }

}
