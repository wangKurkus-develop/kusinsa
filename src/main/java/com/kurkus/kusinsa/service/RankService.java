package com.kurkus.kusinsa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kurkus.kusinsa.utils.constants.RedisCacheConstants.*;

import com.kurkus.kusinsa.dao.RankDao;
import com.kurkus.kusinsa.dto.response.rank.ClickRankResponseV1;
import com.kurkus.kusinsa.dto.response.rank.OrderRankResponse;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class RankService {

    private final RankDao rankDao;
    private final ProductRepository productRepository;

    @Cacheable(value = ORDER_RANK)
    public List<OrderRankResponse> orderRankTop10() {
        List<Long> longs = rankDao.orderRankTop10();
        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < longs.size(); i++) {
            map.put(longs.get(i), i + 1);
        }
        List<Product> list = productRepository.findAllWithBrandByList(longs);

        List<OrderRankResponse> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Product product = list.get(i);
            result.add(OrderRankResponse.of(product, map.get(product.getId())));
        }
        return result;
    }

    @Cacheable(value = CLICK_RANK)
    public List<ClickRankResponseV1> clickRankTop10() {
        List<Long> longs = rankDao.clickRankTop10();
        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < longs.size(); i++) {
            map.put(longs.get(i), i + 1);
        }
        List<Product> list = productRepository.findAllWithBrandByList(longs);

        List<ClickRankResponseV1> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Product product = list.get(i);
            result.add(ClickRankResponseV1.of(product, map.get(product.getId())));
        }
        return result;
    }

    public void resetRankData() {
        rankDao.resetRankData();
    }


    @CacheEvict(value = {ORDER_RANK, CLICK_RANK})
    public void rankCacheEvict() {
        log.info("rank cache evict");
    }


}
