package com.kurkus.kusinsa.entity.documents;

import com.kurkus.kusinsa.dto.response.rank.OrderRankResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order_ranks")
@Getter
@NoArgsConstructor
public class OrderRank {

    @Id
    private String id;
    private Long productId;
    private String name;
    private Long price;
    private int rank;
    private String createdAt;

    public OrderRank(Long productId, String name, Long price, int rank, String createdAt) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.rank = rank;
        this.createdAt = createdAt;
    }

    public static OrderRank of(OrderRankResponse response, String createdAt) {
        return new OrderRank(response.getProductId(), response.getName(), response.getPrice(), response.getRank(), createdAt);
    }
}
