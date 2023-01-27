package com.kurkus.kusinsa.service;

import java.util.ArrayList;

import com.kurkus.kusinsa.dao.RankDao;
import com.kurkus.kusinsa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class RankServiceTest {

    @InjectMocks
    RankService rankService;

    @Mock
    RankDao rankDao;

    @Mock
    ProductRepository productRepository;


    @Test
    void orderRankTop10() {
        // given
        given(rankDao.orderRankTop10()).willReturn(new ArrayList<>());
        given(productRepository.findAllWithBrandByList(anyList())).willReturn(new ArrayList<>());
        // when
        rankService.orderRankTop10();
        // then
        then(rankDao).should(times(1)).orderRankTop10();
        then(productRepository).should(times(1)).findAllWithBrandByList(anyList());
    }

    @Test
    public void clickRankTop10() throws Exception {
        // given
        given(rankDao.clickRankTop10()).willReturn(new ArrayList<>());
        given(productRepository.findAllWithBrandByList(anyList())).willReturn(new ArrayList<>());
        // when
        rankService.clickRankTop10();
        // then
        then(rankDao).should(times(1)).clickRankTop10();
        then(productRepository).should(times(1)).findAllWithBrandByList(anyList());
    }
}