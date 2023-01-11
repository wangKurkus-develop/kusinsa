package com.kurkus.kusinsa.entity;

import javax.persistence.*;

import com.kurkus.kusinsa.dto.request.category.CategoryUpdateRequest;
import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;


    public void update(CategoryUpdateRequest request){
        this.name = request.getCategoryName();
    }

}
