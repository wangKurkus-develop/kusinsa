package com.kurkus.kusinsa.dto.request.point;


import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
public class PointCreateRequest {

    private Long score;
    private PointType division;
    private String content;



    public Point toPoint(User user){
        return Point.builder()
                .score(score)
                .content(content)
                .division(division)
                .user(user)
                .build();
    }


}
