package com.kurkus.kusinsa.dto.response.rank;

import com.kurkus.kusinsa.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClickRankResponseV1 {

    private Long productId;
    private String name;
    private int rank;

    public static ClickRankResponseV1 from(Product product, int rank){
        return new ClickRankResponseV1(product.getId(), product.getName(), rank);
    }

}
