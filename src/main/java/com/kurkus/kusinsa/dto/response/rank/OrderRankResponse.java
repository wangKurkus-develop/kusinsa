package com.kurkus.kusinsa.dto.response.rank;


import com.kurkus.kusinsa.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRankResponse {

    private Long productId;
    private String name;
    private Long price;
    private String thumbnailImagePath;
    // brand
    private Long brandId;
    private String brandName;
    private int rank;

    public static OrderRankResponse from(Product product, int rank){
        return new OrderRankResponse(product.getId(), product.getName(), product.getPrice(), product.getThumbnailImagePath(),
                product.getBrand().getId(), product.getBrand().getName(), rank);
    }

}
