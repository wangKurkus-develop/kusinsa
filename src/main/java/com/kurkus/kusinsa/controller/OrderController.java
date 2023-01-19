package com.kurkus.kusinsa.controller;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.annotation.SessionUserId;
import com.kurkus.kusinsa.dto.request.order.OrderCreateRequest;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @LoginCheck(userType = UserType.USER)
    public ResponseEntity<Void> save(@SessionUserId Long userId, @RequestBody OrderCreateRequest request){
        orderService.save(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
