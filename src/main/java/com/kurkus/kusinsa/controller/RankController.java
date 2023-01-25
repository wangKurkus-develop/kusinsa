package com.kurkus.kusinsa.controller;

import java.util.List;

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

    // 실시간 click과 order를 둘다 보내주어야한다.
    @GetMapping("/order")
    public ResponseEntity<List<OrderRankResponse>> orderRank(){
        return ResponseEntity.ok(rankService.orderRankTop10());
    }





}
