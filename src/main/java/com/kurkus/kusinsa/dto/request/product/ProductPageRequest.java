package com.kurkus.kusinsa.dto.request.product;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ProductPageRequest {

    private Long id; // categoryId이든 BrandId이든 둘중하나임
    private int page;
    private String sortProperty;
}
