package com.kurkus.kusinsa.controller;

import com.kurkus.kusinsa.dto.request.brand.BrandCreateRequest;
import com.kurkus.kusinsa.dto.request.brand.BrandUpdateRequest;
import com.kurkus.kusinsa.dto.response.brand.BrandListResponse;
import com.kurkus.kusinsa.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody BrandCreateRequest request){
        brandService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<BrandListResponse> findAll(){
        return ResponseEntity.ok(brandService.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody BrandUpdateRequest request){
        brandService.update(id, request);
        return ResponseEntity.ok().build();
    }


}
