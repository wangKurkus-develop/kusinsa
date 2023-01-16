package com.kurkus.kusinsa.service;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dto.request.brand.BrandCreateRequest;
import com.kurkus.kusinsa.dto.request.brand.BrandUpdateRequest;
import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.exception.brand.BrandException;
import com.kurkus.kusinsa.exception.brand.BrandNotFoundException;
import com.kurkus.kusinsa.repository.BrandRepository;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @InjectMocks
    BrandService brandService;

    @Mock
    BrandRepository brandRepository;


    @Test
    void save() {
        // given
        String brandName = "테스트브랜드이름";
        given(brandRepository.existsByName(brandName)).willReturn(false);
        // when
        brandService.save(new BrandCreateRequest(brandName));
        // then
        then(brandRepository).should(times(1)).save(any(Brand.class));
    }

    @Test
    public void save_실패_이미존재하는경우() {
        // given
        String brandName = "테스트브랜드이름";
        given(brandRepository.existsByName(brandName)).willReturn(true);
        // when
        BrandException ex = assertThrows(BrandException.class, () -> brandService.save(new BrandCreateRequest(brandName)));
        // then
        assertEquals(EXISTS_BRAND, ex.getMessage());
    }

    @Test
    void findAll() {
        // given
        // when
        brandService.findAll();
        // then
        then(brandRepository).should(times(1)).findAllByOrderByNameAsc();
    }

    @Test
    void update() {
        // given
        Long brandId = 100L;
        BrandUpdateRequest request = new BrandUpdateRequest("브랜드 테스트이름");
        given(brandRepository.getById(anyLong())).willReturn(Brand.builder().name(anyString()).build());
        // when
        brandService.update(brandId, request);
        // then
        then(brandRepository).should(times(1)).getById(anyLong());
    }

    @Test
    public void update_실패_존재안하는경우() {
        // given
        Long brandId = 100L;
        BrandUpdateRequest request = new BrandUpdateRequest("브랜드 테스트이름");
        given(brandRepository.getById(anyLong())).willThrow(new BrandNotFoundException());
        // when
        BrandNotFoundException ex = assertThrows(BrandNotFoundException.class, () -> brandService.update(brandId, request));
        // then
        assertEquals(NOT_FOUND_BRAND, ex.getMessage());
    }
}