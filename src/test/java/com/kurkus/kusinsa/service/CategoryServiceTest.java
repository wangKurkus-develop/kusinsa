package com.kurkus.kusinsa.service;

import java.util.ArrayList;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dto.request.category.CategoryCreateRequest;
import com.kurkus.kusinsa.dto.request.category.CategoryUpdateRequest;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.exception.category.CategoryException;
import com.kurkus.kusinsa.exception.category.CategoryNotFoundException;
import com.kurkus.kusinsa.repository.CategoryRepository;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    private String categoryTestName = "테스트카테고리이름";

    @Test
    void save() {
        // given
        given(categoryRepository.existsByName(anyString())).willReturn(false);
        // when
        categoryService.save(CategoryCreateRequest.builder().name(categoryTestName).build());
        // then
        then(categoryRepository).should(times(1)).save(any(Category.class));
    }

    @Test
    public void save_실패_동일한이름이있는경우() {
        // given
        given(categoryRepository.existsByName(anyString())).willReturn(true);
        // when
        CategoryException ex = assertThrows(CategoryException.class,
                () -> categoryService.save(CategoryCreateRequest.builder().name(categoryTestName).build()));
        // then
        assertEquals(EXISTS_CATEGORY, ex.getMessage());
    }

    @Test
    void findAll() {
        // given
        // when
        categoryService.findAll();
        // then
        then(categoryRepository).should(times(1)).findAllByOrderByNameAsc();
    }

    @Test
    void update() {
        // given
        given(categoryRepository.getById(anyLong())).willReturn(Category.builder().build());
        // when
        categoryService.update(anyLong(), CategoryUpdateRequest.builder().categoryName(categoryTestName).build());
        // then
        then(categoryRepository).should(times(1)).getById(anyLong());
    }

    @Test
    public void update_실패_존재안한는경우() {
        // given
        given(categoryRepository.getById(anyLong())).willThrow(new CategoryNotFoundException());
        // when
        CategoryNotFoundException ex = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.update(anyLong(), CategoryUpdateRequest.builder().categoryName(categoryTestName).build()));
        // then
        assertEquals(NOT_FOUND_CATEGORY, ex.getMessage());
    }
}