package com.kurkus.kusinsa.controller.user;


import java.util.List;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.annotation.SessionUserId;
import com.kurkus.kusinsa.dto.response.prodcut.ProductCommonResponse;
import com.kurkus.kusinsa.dto.response.prodcut.ProductWithRankResponse;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 장바구니 + 좋아요 목록
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    // 좋아요
    @LoginCheck(userType = UserType.USER)
    @PostMapping("/likes/{productId}")
    public ResponseEntity<Void> like(@SessionUserId Long userId, @PathVariable Long productId) {
        cartService.like(userId, productId);
        return ResponseEntity.ok().build();
    }
    // 좋아요 취소
    @LoginCheck(userType = UserType.USER)
    @DeleteMapping("/likes/{productId}")
    public ResponseEntity<Void> disLike(@SessionUserId Long userId, @PathVariable Long productId) {
        cartService.disLike(userId, productId);
        return ResponseEntity.ok().build();
    }

    // 최근본 상품 보여주기
    @LoginCheck(userType = UserType.USER)
    @GetMapping("/recents")
    public ResponseEntity<ProductWithRankResponse> recentProducts(@SessionUserId Long userId){
        return ResponseEntity.ok(cartService.findAllRecent(userId));
    }

    // 좋아요 상품 목록 가져오기
    @LoginCheck(userType = UserType.USER)
    @GetMapping("/likes")
    public ResponseEntity<List<ProductCommonResponse>> likeProducts(@SessionUserId Long userId){
        return ResponseEntity.ok(cartService.getLikeProducts(userId));
    }


}
