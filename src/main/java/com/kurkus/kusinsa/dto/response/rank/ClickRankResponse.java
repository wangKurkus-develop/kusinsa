package com.kurkus.kusinsa.dto.response.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClickRankResponse {

    private int rank;
    private String productName;
    private int updNo;

}
