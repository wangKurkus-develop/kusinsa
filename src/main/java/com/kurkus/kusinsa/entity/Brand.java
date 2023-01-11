package com.kurkus.kusinsa.entity;


import javax.persistence.*;

import com.kurkus.kusinsa.dto.request.brand.BrandUpdateRequest;
import com.kurkus.kusinsa.entity.common.BaseTimeEntity;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Brand extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    public void update(BrandUpdateRequest request){
        this.name = request.getBrandName();
    }

}
