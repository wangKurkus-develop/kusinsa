package com.kurkus.kusinsa.exception.order;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.NOT_FOUND_ORDER_HISTORY;

import com.kurkus.kusinsa.exception.CustomNotFoundException;

public class OrderHistoryNotFoundException extends CustomNotFoundException {

    public OrderHistoryNotFoundException(){
        super(NOT_FOUND_ORDER_HISTORY);
    }
}
