package com.kurkus.kusinsa.controller.order;


import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.enums.DeliveryStatus;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.order.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderUpdateController {

    private final OrderHistoryService orderHistoryService;

    @PatchMapping("/delivery/{orderHistoryId}")
    @LoginCheck(userType = UserType.ADMIN)
    public ResponseEntity<Void> updateDeliveryStatus(@PathVariable Long orderHistoryId,
                                               @RequestParam(name = "delivery") DeliveryStatus deliveryStatus){
        orderHistoryService.updateDeliveryStatus(orderHistoryId, deliveryStatus);
        return ResponseEntity.ok().build();
    }

}
