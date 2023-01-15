package com.kurkus.kusinsa.events.point;

import lombok.Getter;


@Getter
public class PointSavedEvent {

    private Long userId;
    private Long score;
    private String content;

    public PointSavedEvent(Long userId, Long score, String content){
        this.userId = userId;
        this.score = score;
        this.content = content;
    }

}
