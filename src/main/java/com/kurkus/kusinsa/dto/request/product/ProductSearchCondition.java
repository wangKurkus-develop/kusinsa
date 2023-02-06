package com.kurkus.kusinsa.dto.request.product;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ProductSearchCondition {

    private Long categoryId;
    private Long brandId;
}
