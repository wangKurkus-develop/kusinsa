package com.kurkus.kusinsa.service;


import java.util.List;
import java.util.stream.Collectors;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

import com.kurkus.kusinsa.dto.request.category.CategoryCreateRequest;
import com.kurkus.kusinsa.dto.request.category.CategoryUpdateRequest;
import com.kurkus.kusinsa.dto.response.category.CategoryListResponse;
import com.kurkus.kusinsa.dto.response.category.CategoryResponse;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.exception.category.CategoryException;
import com.kurkus.kusinsa.repository.CategoryRepository;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Transactional
    public void save(CategoryCreateRequest request) {
        if(categoryRepository.existsByName(request.getName())){
            throw new CategoryException(EXISTS_CATEGORY, HttpStatus.BAD_REQUEST);
        }
        categoryRepository.save(request.toCategory());
    }


    @Transactional(readOnly = true)
    public CategoryListResponse findAll() {
        List<Category> list = categoryRepository.findAllByOrderByNameAsc();
        return CategoryListResponse.of(list);
    }

    @Transactional
    public void update(Long id, CategoryUpdateRequest request) {
        Category category = categoryRepository.getById(id);
        category.update(request);
    }
}
