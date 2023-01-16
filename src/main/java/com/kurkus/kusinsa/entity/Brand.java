package com.kurkus.kusinsa.entity;


import javax.persistence.*;

import com.kurkus.kusinsa.dto.request.brand.BrandUpdateRequest;
import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert
public class Brand extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean deleted;

    public void update(BrandUpdateRequest request){
        this.name = request.getBrandName();
    }

}
