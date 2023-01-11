package com.kurkus.kusinsa.dto.response.category;


import java.util.List;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.entity.Category;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CategoryListResponse {

    private List<CategoryResponse> categoryList;


    public static CategoryListResponse of(List<Category> list){
        return CategoryListResponse.builder()
                .categoryList(list.stream().map(c -> CategoryResponse.of(c)).collect(Collectors.toList()))
                .build();
    }

}
