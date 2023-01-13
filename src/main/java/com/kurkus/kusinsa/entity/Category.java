package com.kurkus.kusinsa.entity;

import javax.persistence.*;

import com.kurkus.kusinsa.dto.request.category.CategoryUpdateRequest;
import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean deleted;


    public void update(CategoryUpdateRequest request){
        this.name = request.getCategoryName();
    }

}
