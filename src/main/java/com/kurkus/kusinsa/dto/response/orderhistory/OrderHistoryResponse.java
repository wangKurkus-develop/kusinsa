package com.kurkus.kusinsa.dto.response.orderhistory;

import java.time.LocalDate;

import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.enums.DeliveryStatus;
import com.kurkus.kusinsa.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


// 각자하나를 의미함
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderHistoryResponse {

    private Long productId;
    private String thumbnailImagePath;
    private Long brandId;
    private String brandName;
    private String productName;
    private LocalDate orderCreatedAt;
    private Long orderId;
    private Long orderProductPrice;
    private Integer quantity;
    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;

    public static OrderHistoryResponse from(OrderHistory h){
        return OrderHistoryResponse.builder()
                .productId(h.getProduct().getId())
                .thumbnailImagePath(h.getProduct().getThumbnailImagePath())
                .brandId(h.getProduct().getBrand().getId())
                .brandName(h.getProduct().getBrand().getName())
                .productName(h.getProduct().getName())
                .orderCreatedAt(h.getCreatedAt().toLocalDate())
                .orderId(h.getOrder().getId())
                .orderProductPrice(h.getPrice())
                .quantity(h.getQuantity())
                .orderStatus(h.getOrderStatus())
                .deliveryStatus(h.getDeliveryStatus())
                .build();
    }
}
