package com.kurkus.kusinsa.repository.product;


import com.kurkus.kusinsa.dto.request.product.ProductSearchCondition;
import com.kurkus.kusinsa.dto.response.prodcut.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<ProductResponse> searchPageCondition(ProductSearchCondition condition, Pageable pageable);

}
