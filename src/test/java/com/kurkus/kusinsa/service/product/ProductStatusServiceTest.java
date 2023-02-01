package com.kurkus.kusinsa.service.product;

import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dto.request.product.status.ProductStockUpdateRequest;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductStatusServiceTest {

    @InjectMocks
    ProductStatusService productStatusService;

    @Mock
    ProductRepository productRepository;

    @Test
    void updateStock() {
        // given
        ProductStockUpdateRequest request = new ProductStockUpdateRequest(15L, 23L);
        given(productRepository.findByIdWithPessimisticLock(any())).willReturn(Product.builder().build());
        // when
        productStatusService.updateStock(request);
        // then
        then(productRepository).should(times(1)).findByIdWithPessimisticLock(any());
    }
}