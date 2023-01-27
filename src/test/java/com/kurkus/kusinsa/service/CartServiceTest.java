package com.kurkus.kusinsa.service;

import java.util.ArrayList;
import java.util.HashSet;

import com.kurkus.kusinsa.dao.LikesDao;
import com.kurkus.kusinsa.dao.RecentDao;
import com.kurkus.kusinsa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    LikesDao likesDao;

    @Mock
    RecentDao recentDao;

    @Mock
    ProductRepository productRepository;

    @Test
    public void like() throws Exception {
        // given
        Long userId = 18L;
        Long productId = 10L;
        // when
        cartService.like(userId, productId);
        // then
        then(likesDao).should(times(1)).likeProduct(anyLong(), anyLong());
    }

    @Test
    public void disLike() throws Exception {
        // given
        Long userId = 18L;
        Long productId = 10L;
        // when
        cartService.disLike(userId, productId);
        // then
        then(likesDao).should(times(1)).disLikeProduct(anyLong(), anyLong());
    }


    @Test
    public void findAllRecent() throws Exception {
        // given
        Long userId = 16L;
        given(recentDao.findAllRecent(userId)).willReturn(new HashSet<>());
        given(productRepository.findAllWithBrandByList(any())).willReturn(new ArrayList<>());
        // when
        cartService.findAllRecent(userId);
        // then
        then(recentDao).should(times(1)).findAllRecent(anyLong());
        then(productRepository).should(times(1)).findAllWithBrandByList(any());
    }


}