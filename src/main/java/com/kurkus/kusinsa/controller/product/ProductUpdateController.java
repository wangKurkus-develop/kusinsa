package com.kurkus.kusinsa.controller.product;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.dto.request.product.status.ProductStockUpdateRequest;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.product.ProductStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products/status")
public class ProductUpdateController {

    private final ProductStatusService productStatusService;

    // 재고변경 (재입고)
    // 상품번호, 개수가 끝일듯
    @PatchMapping("/stock")
    @LoginCheck(userType = UserType.ADMIN)
    public ResponseEntity<Void> updateStock(@RequestBody ProductStockUpdateRequest request){
        productStatusService.updateStock(request);
        return ResponseEntity.ok().build();
    }

    // 재고변경 일괄적으로 변경하는것도 추가하기
    // 판매중지나 추가하기

}
