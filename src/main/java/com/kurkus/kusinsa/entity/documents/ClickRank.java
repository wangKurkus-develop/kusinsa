package com.kurkus.kusinsa.entity.documents;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kurkus.kusinsa.dto.response.rank.ClickRankResponseV1;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "click_ranks")
@Getter
@NoArgsConstructor
public class ClickRank {

    @Id
    private String id;
    private Long productId;
    private String name;
    private int rank;
    private String createdAt;

    public ClickRank(Long productId, String name, int rank, String createdAt){
        this.productId = productId;
        this.name = name;
        this.rank = rank;
        this.createdAt = createdAt;
    }


    public static ClickRank of(ClickRankResponseV1 response, String createdAt){
        return new ClickRank(response.getProductId(), response.getName(), response.getRank(), createdAt);
    }

}
