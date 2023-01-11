package com.kurkus.kusinsa.dto.request.product;

import com.kurkus.kusinsa.enums.ProductType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProductUpdateRequest {

    private String name;
    private Long price;
    private String content;
    private long stock;
    private ProductType status;
}
