package com.kurkus.kusinsa.controller;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.dto.request.category.CategoryCreateRequest;
import com.kurkus.kusinsa.dto.request.category.CategoryUpdateRequest;
import com.kurkus.kusinsa.dto.response.category.CategoryListResponse;
import com.kurkus.kusinsa.dto.response.category.CategoryResponse;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorys")
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping
    @LoginCheck(userType = UserType.ADMIN)
    public ResponseEntity<Void> save(@RequestBody CategoryCreateRequest request){
        categoryService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(){
        CategoryListResponse response = categoryService.findAll();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @LoginCheck(userType = UserType.ADMIN)
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CategoryUpdateRequest request){
        categoryService.update(id, request);
        return ResponseEntity.ok().build();
    }



}
