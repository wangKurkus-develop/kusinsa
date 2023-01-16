package com.kurkus.kusinsa.events.point;

import lombok.Getter;


@Getter
public class PointLoginSavedEvent {

    private Long userId;
    private Long score;
    private String content;

    public PointLoginSavedEvent(Long userId, Long score, String content){
        this.userId = userId;
        this.score = score;
        this.content = content;
    }

}
