package com.kurkus.kusinsa.dto.request.point;

import com.kurkus.kusinsa.enums.PointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PointPageRequest {

    private int page;
    private int size;
    private PointType pointType;
}
