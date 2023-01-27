package com.kurkus.kusinsa.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.dao.LikesDao;
import com.kurkus.kusinsa.dao.RecentDao;
import com.kurkus.kusinsa.dto.response.prodcut.ProductCommonResponse;
import com.kurkus.kusinsa.repository.ProductRepository;
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


    public List<ProductCommonResponse> findAllRecent(Long userId) {
        Set<Object> set = recentDao.findAllRecent(userId);
        List<Long> list = new ArrayList<>();
        Iterator<Object> iterator = set.iterator();
        while(iterator.hasNext()){
            list.add((long)iterator.next());
        }
        List<ProductCommonResponse> result = productRepository.findAllWithBrandByList(list).stream()
                .map(p -> ProductCommonResponse.from(p)).collect(Collectors.toList());
        return result;
    }
}
