package com.kurkus.kusinsa.controller;

import java.util.List;

import com.kurkus.kusinsa.dto.response.rank.ClickRankResponseV1;
import com.kurkus.kusinsa.dto.response.rank.OrderRankResponse;
import com.kurkus.kusinsa.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ranks")
public class RankController {

    private final RankService rankService;

    @GetMapping("/order")
    public ResponseEntity<List<OrderRankResponse>> orderRank(){
        return ResponseEntity.ok(rankService.orderRankTop10());
    }

    @GetMapping("/click")
    public ResponseEntity<List<ClickRankResponseV1>> clickRank(){
        return ResponseEntity.ok(rankService.clickRankTop10());
    }





}
