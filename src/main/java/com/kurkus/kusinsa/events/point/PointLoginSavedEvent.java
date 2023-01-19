package com.kurkus.kusinsa.events.point;

import lombok.Getter;


@Getter
public class PointLoginSavedEvent {

    private Long userId;

    public PointLoginSavedEvent(Long userId){
        this.userId = userId;
    }

}
