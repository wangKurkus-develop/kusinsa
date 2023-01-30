package com.kurkus.kusinsa.service.product;

import com.kurkus.kusinsa.dto.request.product.status.ProductStockUpdateRequest;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductStatusService {

    private final ProductRepository productRepository;


    /**
     * lock
     * update (stock, status)
     * alarm
     */
    @Transactional
    public void updateStock(ProductStockUpdateRequest request) {
        Product product = productRepository.findByIdWithPessimisticLock(request.getProductId());
        product.updateStock(request.getStock());
        /**
         * 알림 기능
         */
    }
}
