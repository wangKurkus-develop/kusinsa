package com.kurkus.kusinsa.controller;

import com.kurkus.kusinsa.dto.request.product.ProductCreateRequest;
import com.kurkus.kusinsa.dto.request.product.ProductPageRequest;
import com.kurkus.kusinsa.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ProductControllerTest {

    @Autowired
    ProductService productService;

    private String name = "상품1";
    private Long price = 1000L;
    private String content = "상품1의 설명";
    private Long categoryId = 1L;
    private Long brandId =  1L;
    private String originImagePath = "원본경로1";
    private String thumbnailImagePath = "썸네일경로1";

    private ProductCreateRequest getProductCreateRequest(){
        return ProductCreateRequest.builder()
                .name(name)
                .price(price)
                .content(content)
                .categoryId(categoryId)
                .brandId(brandId)
                .originImagePath(originImagePath)
                .thumbnailImagePath(thumbnailImagePath)
                .build();
    }

    private ProductPageRequest getProductPageRequest(){
        return ProductPageRequest.builder()
                .id(1L)
                .page(0)
                .sortProperty("steams")
                .build();
    }

    @Test
    void save() {
        productService.save(getProductCreateRequest());
    }

    @Test
    void findById() {
        productService.findById(1L);
    }

    @Test
    void update() {
    }

    @Test
    void findAllByCategory() {
        productService.findAllByCategory(getProductPageRequest());
    }
}