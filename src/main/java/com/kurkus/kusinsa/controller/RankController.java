package com.kurkus.kusinsa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ranks")
public class RankingController {


    @GetMapping("/orders")
    public ResponseEntity<Void> rankOrders(){

    }

    @GetMapping("/clicks")
    public ResponseEntity<Void> rankClick(){
        
    }


}
