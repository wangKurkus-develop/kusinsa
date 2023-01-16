package com.kurkus.kusinsa.dto.response.point;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.kurkus.kusinsa.entity.Point;
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
    private LocalDate createdAt;


    public static PointResponse from(Point point){
        return PointResponse.builder()
                .id(point.getId())
                .score(point.getScore())
                .division(point.getDivision())
                .content(point.getContent())
                .createdAt(point.getCreatedAt().toLocalDate())
                .build();
    }

}