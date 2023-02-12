package com.kurkus.kusinsa.service.product;

import java.util.ArrayList;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.ErrorMessages.EXISTS_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dao.LikesDao;
import com.kurkus.kusinsa.dto.request.product.ProductCreateRequest;
import com.kurkus.kusinsa.dto.request.product.ProductSearchCondition;
import com.kurkus.kusinsa.dto.request.product.ProductUpdateRequest;
import com.kurkus.kusinsa.dto.response.prodcut.ProductResponse;
import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.enums.ProductType;
import com.kurkus.kusinsa.exception.brand.BrandNotFoundException;
import com.kurkus.kusinsa.exception.category.CategoryNotFoundException;
import com.kurkus.kusinsa.exception.product.ProductException;
import com.kurkus.kusinsa.exception.product.ProductNotFoundException;
import com.kurkus.kusinsa.repository.BrandRepository;
import com.kurkus.kusinsa.repository.CategoryRepository;
import com.kurkus.kusinsa.repository.product.ProductRepository;
import com.kurkus.kusinsa.utils.PasswordEncoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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

    @Test
    public void findById() {
        // given
        Product product = Product.builder()
                .id(1L)
                .name(name)
                .content(content)
                .price(price)
                .stock(1)
                .status(ProductType.SOLD_OUT)
                .thumbnailImagePath(thumbnailImagePath)
                .originImagePath(originImagePath)
                .brand(Brand.builder().id(brandId).name("브랜드이름").build())
                .category(Category.builder().id(categoryId).name("카테고리이름").build())
                .build();
        given(productRepository.getByIdWithAll(anyLong())).willReturn(product);
        // when
        productService.findById(anyLong());
        // then
        then(productRepository).should(times(1)).getByIdWithAll(anyLong());
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

    @Test
    public void delete() throws Exception {
        // given
        given(productRepository.getById(anyLong())).willReturn(Product.builder().build());
        // when
        productService.delete(anyLong());
        // then
        then(productRepository).should(times(1)).getById(anyLong());
    }

    @Test
    public void searchCondition() throws Exception {
        // given
        given(productRepository.searchPageCondition(any(), any())).willReturn(new PageImpl(new ArrayList()));
        // when
        productService.searchCondition(ProductSearchCondition.builder().build(), PageRequest.of(1,2));
        // then
        then(productRepository).should(times(1)).searchPageCondition(any(), any());
    }





}