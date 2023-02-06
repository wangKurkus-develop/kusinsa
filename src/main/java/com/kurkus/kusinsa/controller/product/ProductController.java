package com.kurkus.kusinsa.controller.product;

import static com.kurkus.kusinsa.utils.constants.PageSizeConstants.*;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.dto.request.product.ProductCreateRequest;
import com.kurkus.kusinsa.dto.request.product.ProductSearchCondition;
import com.kurkus.kusinsa.dto.request.product.ProductUpdateRequest;
import com.kurkus.kusinsa.dto.response.prodcut.ProductResponse;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.product.ProductService;
import com.kurkus.kusinsa.utils.constants.PageSizeConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> searchCondition(@RequestBody ProductSearchCondition request,
                                                                 @PageableDefault(size = PRODUCT_SIZE, sort = "createdAt",
                                                                         direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(productService.searchCondition(request, pageable));
    }




}
