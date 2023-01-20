package com.kurkus.kusinsa.dto.response.orderhistory;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.kurkus.kusinsa.enums.DeliveryStatus;
import com.kurkus.kusinsa.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class OrderHistoryResponse {

    // history
    private Long historyId;
    private Long orderProductPrice;
    private Integer quantity;
    private DeliveryStatus deliveryStatus;
    private OrderStatus orderStatus;
    // product
    private Long productId;
    private String thumbnailImagePath;
    private String productName;
    // brand
    private Long brandId;
    private String brandName;
    // order
    private LocalDate orderCreatedAt;
    private Long orderId;


    public OrderHistoryResponse(Long historyId,
            Long productId, String thumbnailImagePath, String productName, Long brandId, String brandName,
                                LocalDateTime orderCreatedAt, Long orderId, Long orderProductPrice,
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
        this.historyId = historyId;
    }

}
