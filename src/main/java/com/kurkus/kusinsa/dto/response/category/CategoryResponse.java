package com.kurkus.kusinsa.dto.response.category;


import com.kurkus.kusinsa.entity.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {

    private Long id;
    private String name;

    private CategoryResponse(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static CategoryResponse from(Category category){
        return new CategoryResponse(category.getId(), category.getName());
    }
}
