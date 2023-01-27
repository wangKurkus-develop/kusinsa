package com.kurkus.kusinsa.dto.response.rank;

import com.kurkus.kusinsa.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClickRankResponseV1 {

    private Long productId;
    private String name;
    private int rank;

    public static ClickRankResponseV1 of(Product product, int rank){
        return new ClickRankResponseV1(product.getId(), product.getName(), rank);
    }

}
