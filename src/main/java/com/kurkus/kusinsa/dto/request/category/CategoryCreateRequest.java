package com.kurkus.kusinsa.dto.request.category;


import com.kurkus.kusinsa.entity.Category;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryCreateRequest {

    private String name;

    public Category toCategory(){
        return Category.builder()
                .name(name)
                .build();
    }
}
