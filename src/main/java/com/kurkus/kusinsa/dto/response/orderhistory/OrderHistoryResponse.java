package com.kurkus.kusinsa.dto.response.orderhistory;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class OrderHistoryResponse {

    // produt
    private Long productId;
    private String thumbnailImagePath;
    private String productName;
    // brand
    private Long brandId;
    private String brandName;
    // order
    private LocalDate orderCreatedAt;
    private Long orderId;
    // history
    private Long orderProductPrice;
    private Integer quantity;
    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;

    public OrderHistoryResponse(Long productId, String thumbnailImagePath, String productName, Long brandId, String brandName, LocalDateTime orderCreatedAt, Long orderId, Long orderProductPrice,
                                Integer quantity, OrderStatus orderStatus, DeliveryStatus deliveryStatus) {
        this.productId = productId;
        this.thumbnailImagePath = thumbnailImagePath;
        this.productName = productName;
        this.brandId = brandId;
        this.brandName = brandName;
        this.orderCreatedAt = orderCreatedAt.toLocalDate();
        this.orderId = orderId;
        this.orderProductPrice = orderProductPrice;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
    }

}
