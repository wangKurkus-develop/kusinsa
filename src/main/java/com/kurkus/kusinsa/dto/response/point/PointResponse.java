package com.kurkus.kusinsa.dto.request.point;


import javax.persistence.*;

import com.kurkus.kusinsa.entity.Point;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.PointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PointResponse {

    private Long id;
    private Long score;
    private PointType division;
    private String content;


    public static PointResponse of(Point point){
        return PointResponse.builder()
                .id(point.getId())
                .score(point.getScore())
                .division(point.getDivision())
                .content(point.getContent())
                .build();
    }

}
