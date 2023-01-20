package com.kurkus.kusinsa.controller;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.annotation.SessionUserId;
import com.kurkus.kusinsa.dto.request.order.OrderCreateRequest;
import com.kurkus.kusinsa.dto.response.orderhistory.OrderHistoryResponse;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.order.OrderHistoryService;
import com.kurkus.kusinsa.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderHistoryService historyService;

    @PostMapping
    @LoginCheck(userType = UserType.USER)
    public ResponseEntity<Void> save(@SessionUserId Long userId, @RequestBody OrderCreateRequest request){
        orderService.save(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping
    @LoginCheck(userType = UserType.USER)
    public ResponseEntity<Page<OrderHistoryResponse>> findAll(@SessionUserId Long userId,
                                                              @RequestParam(name = "page", defaultValue = "0") int page){
        return ResponseEntity.ok(historyService.findAllWithPage(userId, page));
    }

    // 주문 취소 만들기
    @PatchMapping("/{historyId}")
    @LoginCheck(userType = UserType.USER)
    public ResponseEntity<Void> orderCancel(@SessionUserId Long userId, @PathVariable Long historyId){
        historyService.cancel(userId, historyId);
        return ResponseEntity.ok().build();
    }


}
