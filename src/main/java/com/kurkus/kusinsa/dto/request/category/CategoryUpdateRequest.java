package com.kurkus.kusinsa.dto.request.category;

import lombok.*;


@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryUpdateRequest {

    private String categoryName;

}
