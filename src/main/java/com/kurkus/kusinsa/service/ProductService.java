package com.kurkus.kusinsa.service;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.PageSizeConstants.*;

import com.kurkus.kusinsa.dto.request.product.ProductCreateRequest;
import com.kurkus.kusinsa.dto.request.product.ProductPageRequest;
import com.kurkus.kusinsa.dto.request.product.ProductUpdateRequest;
import com.kurkus.kusinsa.dto.response.brand.BrandResponse;
import com.kurkus.kusinsa.dto.response.category.CategoryResponse;
import com.kurkus.kusinsa.dto.response.prodcut.ProductAllResponse;
import com.kurkus.kusinsa.dto.response.prodcut.ProductResponse;
import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.exception.product.ProductException;
import com.kurkus.kusinsa.repository.BrandRepository;
import com.kurkus.kusinsa.repository.CategoryRepository;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.utils.constants.PageSizeConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    /**
     * 이름 중복체크
     */
    @Transactional
    public void save(ProductCreateRequest request) {
        if(productRepository.findByName(request.getName()).isPresent()){
            throw new ProductException(EXISTS_PRODUCT, HttpStatus.BAD_REQUEST);
        }

        Category category = categoryRepository.getById(request.getCategoryId());
        Brand brand = brandRepository.getById(request.getBrandId());
        Product product = request.toProduct(category, brand);
        productRepository.save(product);
    }

    /**
     * v1 : fetch join으로 카테고리, 브랜드까지 반환을 합니다
     * v2 : React에서는 list로 가지고있어서 카테고리랑 브랜드까지는 반환안해도될것같음
     * update나 findById에서는 있는지 체크하는것은 불필요한거같다 왜냐하면 없으면 업데이트를 안하고 더티체킹을 위해서 상관없지만
     * 없으면 없는정보를 주면되기때문이다.
     * v3 : 아니다 jpa에서는 of메서드라던지 toEntity라는 메서드를 활용하기때문에 NPE문제가 발생하므로 Null 체크를해줘야합니다.
     */
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.getByIdWithAll(id);
        return ProductResponse.of(product);
    }

    /**
     * v2 : 이미지, category따로 빼기 아니면 합치기
     */
    @Transactional
    public void update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.getById(id);
        product.update(request);
    }

    /**
     * 페이징 성능개선에 포함됨
     * 정렬기준을 parameter로 받을지 request로 받을지고민이된다...
     * 테스트코드시 Page객체를 response를 해야하는것도 동적쿼리 작성후에 작성하기
     */
    @Transactional(readOnly = true)
    public Page<ProductAllResponse> findAllByCategory(ProductPageRequest request){
        Page<Product> productPage= productRepository.findAllByCategory(request.getId(),
                PageRequest.of(request.getPage(), PRODUCT_SIZE,
                        Sort.by(Sort.Direction.DESC,request.getSortProperty())));

        Page<ProductAllResponse> response = productPage.map(p -> ProductAllResponse.of(p));
        return response;
    }

    // 페이지로 반환하는 findAll (category, brand도 함께)
    // 브랜드별로 반환 (정렬기준은 steams 개수) 아니면 정렬기준을 받는것도 괜찮음
    // 카테고리별로 반환 (정렬기준은 steam )

    @Transactional
    public void delete(Long id){
        Product product = productRepository.getById(id);
        product.delete();
    }
}
