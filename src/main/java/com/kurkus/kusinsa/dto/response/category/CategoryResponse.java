package com.kurkus.kusinsa.dto.response.category;


import com.kurkus.kusinsa.entity.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {

    private Long id;
    private String name;

    public CategoryResponse(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static CategoryResponse of(Category category){
        return new CategoryResponse(category.getId(), category.getName());
    }
}
