package com.kurkus.kusinsa.dto.request.product.status;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockUpdateRequest {

    private Long productId;
    private long stock;
}
