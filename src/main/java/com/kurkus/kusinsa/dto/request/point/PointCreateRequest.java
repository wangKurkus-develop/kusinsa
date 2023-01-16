package com.kurkus.kusinsa.dto.request.point;

import javax.validation.constraints.Min;

import com.kurkus.kusinsa.entity.Point;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.PointType;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 만약 valid체크가 Obtain도 다르게 추가된다면 위험이있기때문에 이렇게 Request를 분리시켰다
 */
@NoArgsConstructor
@Getter
public class PointCreateRequest {

    @Min(value = 1000, message = "적립금 전환은 1,000점 이상부터 가능합니다")
    private Long score;
    private String content;
    private PointType division;

    public PointCreateRequest(Long score, String content, PointType division){
        this.score = score;
        this.content = content;
        this.division = division;
    }

    public Point toPoint(User user){
        return Point.builder()
                .score(score)
                .content(content)
                .division(division)
                .user(user)
                .build();
    }
}
