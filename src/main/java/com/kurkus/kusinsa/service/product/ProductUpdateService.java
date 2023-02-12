package com.kurkus.kusinsa.service.product;

import com.kurkus.kusinsa.dto.request.product.status.ProductStockUpdateRequest;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.events.notification.GroupEvent;
import com.kurkus.kusinsa.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductUpdateService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher publisher;


    @Transactional
    public void updateStock(ProductStockUpdateRequest request) {
        Product product = productRepository.findByIdWithPessimisticLock(request.getProductId());
        product.updateStock(request.getStock());
        publisher.publishEvent(new GroupEvent(product.getId(), product.getName()));
    }
}
