package com.kurkus.kusinsa.dto.request.order;


import com.kurkus.kusinsa.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductRequest {

    // productId
    private Long productId;
    // 수량
    private Integer quantity;
    // 금액
    private Long price;
    // 획득 포인트 (기록을 위한거임)
    private long obtainPoint;

    private Product product;

    public void addProduct(Product product){
        this.product = product;
    }



}
