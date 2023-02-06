package com.kurkus.kusinsa.dto.request.point;


import com.kurkus.kusinsa.enums.PointType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointSearchCondition {

    private PointType division;
    private Long userId;

}
