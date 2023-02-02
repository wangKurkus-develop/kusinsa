package com.kurkus.kusinsa.dto.request.product;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ProductSearchConditionRequest {

    private Long id; //
    private int page;
    private String sortProperty; // 이름인지 날짜순인지
}
