package com.kurkus.kusinsa.service.product;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

import com.kurkus.kusinsa.dto.request.product.ProductCreateRequest;
import com.kurkus.kusinsa.dto.request.product.ProductSearchCondition;
import com.kurkus.kusinsa.dto.request.product.ProductUpdateRequest;
import com.kurkus.kusinsa.dto.response.prodcut.ProductResponse;
import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.exception.product.ProductException;
import com.kurkus.kusinsa.repository.BrandRepository;
import com.kurkus.kusinsa.repository.CategoryRepository;
import com.kurkus.kusinsa.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public void save(ProductCreateRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new ProductException(EXISTS_PRODUCT, HttpStatus.BAD_REQUEST);
        }

        Category category = categoryRepository.getById(request.getCategoryId());
        Brand brand = brandRepository.getById(request.getBrandId());
        Product product = request.toProduct(category, brand);
        productRepository.save(product);
    }

    /**
     * v3 : 아니다 jpa에서는 of메서드라던지 toEntity라는 메서드를 활용하기때문에 NPE문제가 발생하므로 Null 체크를해줘야합니다.
     * v4 : dto로 바로 가져오는것이 제일좋은것같다 불필요하게 createdAt이런거를 가져와야할가?
     */
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.getByIdWithAll(id);
        return ProductResponse.from(product);
    }

    /**
     * v2 : 이미지, category따로 빼기 아니면 합치기
     */
    @Transactional
    public void update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.getById(id);
        product.update(request);
    }


    @Transactional
    public void delete(Long id) {
        Product product = productRepository.getById(id);
        product.delete();
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> searchCondition(ProductSearchCondition request, Pageable pageable) {
        return productRepository.searchPageCondition(request, pageable);
    }






}
