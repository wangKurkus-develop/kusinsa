package com.kurkus.kusinsa.service.product;

import com.kurkus.kusinsa.dto.request.product.status.ProductStockUpdateRequest;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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
        log.info("재고 이벤트 발생 단체문자보내기");
    }
}
