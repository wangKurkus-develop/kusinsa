package com.kurkus.kusinsa.service.product;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.ErrorMessages.EXISTS_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dao.LikesDao;
import com.kurkus.kusinsa.dto.request.product.ProductCreateRequest;
import com.kurkus.kusinsa.dto.request.product.ProductUpdateRequest;
import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.exception.brand.BrandNotFoundException;
import com.kurkus.kusinsa.exception.category.CategoryNotFoundException;
import com.kurkus.kusinsa.exception.product.ProductException;
import com.kurkus.kusinsa.exception.product.ProductNotFoundException;
import com.kurkus.kusinsa.repository.BrandRepository;
import com.kurkus.kusinsa.repository.CategoryRepository;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.service.product.ProductService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    BrandRepository brandRepository;

    @Mock
    LikesDao likesDao;

    private String name = "상품1";
    private Long price = 1000L;
    private String content = "상품1의 설명";
    private Long categoryId = 1L;
    private Long brandId = 1L;
    private String originImagePath = "원본경로1";
    private String thumbnailImagePath = "썸네일경로1";

    private ProductCreateRequest getCreateRequest() {
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


    @Nested
    class save {

        @Test
        public void 성공() {
            // given
            given(productRepository.existsByName(anyString())).willReturn(false);
            given(categoryRepository.getById(anyLong())).willReturn(Category.builder().build());
            given(brandRepository.getById(anyLong())).willReturn(Brand.builder().build());
            // when
            productService.save(getCreateRequest());
            // then
            then(productRepository).should(times(1)).save(any(Product.class));
        }

        @Test
        public void 실패_이미존재하는경우() {
            // given
            given(productRepository.existsByName(anyString())).willReturn(true);
            // when
            ProductException ex = assertThrows(ProductException.class, () -> productService.save(getCreateRequest()));
            // then
            assertEquals(EXISTS_PRODUCT, ex.getMessage());
        }

        @Test
        public void 실패_카테고리존재_x() {
            // given
            given(productRepository.existsByName(anyString())).willReturn(false);
            given(categoryRepository.getById(anyLong())).willThrow(new CategoryNotFoundException());
            // when
            CategoryNotFoundException ex = assertThrows(CategoryNotFoundException.class, () -> productService.save(getCreateRequest()));
            // then
            assertEquals(NOT_FOUND_CATEGORY, ex.getMessage());
        }

        @Test
        public void 실패_브랜드존재_x() {
            // given
            given(productRepository.existsByName(anyString())).willReturn(false);
            given(categoryRepository.getById(anyLong())).willReturn(Category.builder().build());
            given(brandRepository.getById(anyLong())).willThrow(new BrandNotFoundException());
            // when
            BrandNotFoundException ex = assertThrows(BrandNotFoundException.class, () -> productService.save(getCreateRequest()));
            // then
            assertEquals(NOT_FOUND_BRAND, ex.getMessage());
        }
    }


    @Nested
    class findById {

        @Test
        public void 성공() {
            // given
            given(productRepository.getByIdWithAll(anyLong())).willReturn(Product.builder().build());
            // when
            productService.findById(anyLong());
            // then
            then(productRepository).should(times(1)).getByIdWithAll(anyLong());
        }
    }

    @Nested
    class update {

        @Test
        public void 성공() {
            // given
            given(productRepository.getById(anyLong())).willReturn(Product.builder().build());
            // when
            productService.update(anyLong(), ProductUpdateRequest.builder().build());
            // then
            then(productRepository).should(times(1)).getById(anyLong());
        }

        @Test
        public void 실패_아이디존재_x() {
            // given
            given(productRepository.getById(anyLong())).willThrow(new ProductNotFoundException());
            // when
            ProductNotFoundException ex = assertThrows(ProductNotFoundException.class,
                    () -> productService.update(anyLong(), ProductUpdateRequest.builder().build()));
            // then
            assertEquals(NOT_FOUND_PRODUCT, ex.getMessage());
        }
    }





}