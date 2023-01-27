package com.kurkus.kusinsa.dto.response.category;


import java.util.List;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.entity.Category;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CategoryListResponse {

    private List<CategoryResponse> categoryList;

    private CategoryListResponse(List<CategoryResponse> list){
        this.categoryList = list;
    }

    public static CategoryListResponse from(List<Category> list){
        return new CategoryListResponse(list.stream().map(c -> CategoryResponse.from(c)).collect(Collectors.toList()));
    }

}
