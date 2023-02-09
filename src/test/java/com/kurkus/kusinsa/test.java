package com.kurkus.kusinsa;


import com.kurkus.kusinsa.dto.request.product.ProductSearchCondition;
import com.kurkus.kusinsa.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@SpringBootTest
public class test {

    @Autowired
    ProductService productService;


    @Test
    public void V4() {
        long start = System.currentTimeMillis();
        productService.searchCondition(ProductSearchCondition.builder().categoryId(44L)
                .build(), PageRequest.of(1, 90, Sort.by("price").descending()));
        long end = System.currentTimeMillis();
        System.out.println("V4 수행시간: " + (end - start) + " ms");
    }


}
