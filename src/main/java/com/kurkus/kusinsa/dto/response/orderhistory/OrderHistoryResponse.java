package com.kurkus.kusinsa.dto.response.orderhistory;

import java.time.LocalDate;

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

    private String thumbnailImagePath;
    private String brandName;
    private String productName;
    private LocalDate orderCreatedAt;
    private Long orderId;
    private Long orderProductPrice;
    private Integer quantity;
    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;
}
