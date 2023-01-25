package com.kurkus.kusinsa.controller;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.dto.request.product.ProductCreateRequest;
import com.kurkus.kusinsa.dto.request.product.ProductPageRequest;
import com.kurkus.kusinsa.dto.request.product.ProductUpdateRequest;
import com.kurkus.kusinsa.dto.response.prodcut.ProductAllResponse;
import com.kurkus.kusinsa.dto.response.prodcut.ProductResponse;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @LoginCheck(userType = UserType.ADMIN)
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ProductCreateRequest request) {
        productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @LoginCheck(userType = UserType.ADMIN)
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        productService.update(id, request);
        return ResponseEntity.ok().build();
    }

    /**
     * Controller Search Controller 만들어서 하기
     * 맨투맨 별로 Serach니까 (검색창이아니더라도)
     * QueryDSL로 동적쿼리로 만들기 pathvariable 받아가지고
     *
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<Page<ProductAllResponse>> findAllByCategory(@RequestBody ProductPageRequest request) {
        return ResponseEntity.ok(productService.findAllByCategory(request));
    }



}
