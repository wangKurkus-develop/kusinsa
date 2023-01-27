package com.kurkus.kusinsa.dto.response.rank;


import com.kurkus.kusinsa.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRankResponse {

    private Long productId;
    private String name;
    private Long price;
    private String thumbnailImagePath;
    // brand
    private Long brandId;
    private String brandName;
    private int rank;

    public static OrderRankResponse of(Product product, int rank){
        return new OrderRankResponse(product.getId(), product.getName(), product.getPrice(), product.getThumbnailImagePath(),
                product.getBrand().getId(), product.getBrand().getName(), rank);
    }

}
