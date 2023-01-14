package com.kurkus.kusinsa.events.point;

import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import lombok.Getter;
import lombok.ToString;


@Getter
public class PointSavedEvent {

    private String userId;
    private Long score;
    private String content;

    public PointSavedEvent(String userId, Long score, String content){
        this.userId = userId;
        this.score = score;
        this.content = content;
    }

}
