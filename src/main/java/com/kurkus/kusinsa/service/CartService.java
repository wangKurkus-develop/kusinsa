package com.kurkus.kusinsa.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.dao.LikesDao;
import com.kurkus.kusinsa.dao.RecentDao;
import com.kurkus.kusinsa.dto.response.prodcut.ProductCommonResponse;
import com.kurkus.kusinsa.dto.response.prodcut.ProductWithRankResponse;
import com.kurkus.kusinsa.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartService {

    private final LikesDao likesDao;
    private final RecentDao recentDao;
    private final ProductRepository productRepository;

    public void like(Long userId, Long productId) {
        likesDao.likeProduct(userId, productId);
    }

    public void disLike(Long userId, Long productId) {
        likesDao.disLikeProduct(userId, productId);
    }


    public ProductWithRankResponse findAllRecent(Long userId) {
        Set<Object> set = recentDao.findAllRecent(userId);
        List<Long> list = set.stream().map(o -> new Long(o.toString())).collect(Collectors.toList());
        List<ProductCommonResponse> result = productRepository.findAllWithBrandByList(list).stream()
                .map(p -> ProductCommonResponse.from(p)).collect(Collectors.toList());

        return new ProductWithRankResponse(result, list);
    }

    public List<ProductCommonResponse> getLikeProducts(Long userId){
        List<Long> likeProducts = likesDao.getLikeProducts(userId);
        return productRepository.findAllWithBrandByList(likeProducts).stream()
                .map(p -> ProductCommonResponse.from(p)).collect(Collectors.toList());
    }
}
