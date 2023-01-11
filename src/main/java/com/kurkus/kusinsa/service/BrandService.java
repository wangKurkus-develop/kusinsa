package com.kurkus.kusinsa.service;

import java.util.List;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

import com.kurkus.kusinsa.dto.request.brand.BrandCreateRequest;
import com.kurkus.kusinsa.dto.request.brand.BrandUpdateRequest;
import com.kurkus.kusinsa.dto.response.brand.BrandListResponse;
import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.exception.brand.BrandException;
import com.kurkus.kusinsa.repository.BrandRepository;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;


    @Transactional
    public void save(BrandCreateRequest request) {
        if(brandRepository.existsByName(request.getBrandName())){
            throw new BrandException(EXISTS_BRAND, HttpStatus.BAD_REQUEST);
        }
        brandRepository.save(request.toBrand());
    }

    @Transactional(readOnly = true)
    public BrandListResponse findAll() {
        List<Brand> list = brandRepository.findAllOrderByNameAsc();
        return BrandListResponse.of(list);
    }

    @Transactional
    public void update(Long id, BrandUpdateRequest request) {
        Brand brand = brandRepository.getById(id);
        brand.update(request);
    }
}
