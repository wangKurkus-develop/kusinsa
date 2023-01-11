package com.kurkus.kusinsa.exception.product;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

import com.kurkus.kusinsa.exception.CustomNotFoundException;


public class ProductNotFoundException extends CustomNotFoundException {


    public ProductNotFoundException(){
        super(NOT_FOUND_PRODUCT);
    }
}
