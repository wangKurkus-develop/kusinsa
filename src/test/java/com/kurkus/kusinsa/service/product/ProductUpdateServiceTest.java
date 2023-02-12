package com.kurkus.kusinsa.service.product;

import com.kurkus.kusinsa.dto.request.product.status.ProductStockUpdateRequest;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.events.notification.GroupEvent;
import com.kurkus.kusinsa.repository.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUpdateServiceTest {

    @InjectMocks
    ProductUpdateService productUpdateService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ApplicationEventPublisher publisher;

    @Test
    void updateStock() {
        // given
        ProductStockUpdateRequest request = new ProductStockUpdateRequest(15L, 23L);
        given(productRepository.findByIdWithPessimisticLock(any())).willReturn(Product.builder().id(15L).name("상품이름").build());
        // when
        productUpdateService.updateStock(request);
        // then
        then(productRepository).should(times(1)).findByIdWithPessimisticLock(any());
        then(publisher).should(times(1)).publishEvent(any(GroupEvent.class));
    }
}