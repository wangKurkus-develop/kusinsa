package com.kurkus.kusinsa.scheduler;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.dao.LikesDao;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.documents.ClickRank;
import com.kurkus.kusinsa.entity.documents.OrderRank;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.repository.mongo.ClickRankRepository;
import com.kurkus.kusinsa.repository.mongo.OrderRankRepository;
import com.kurkus.kusinsa.service.RankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Component
public class CronScheduler {


    private final RankService rankService;
    private final LikesDao likesDao;
    private final ClickRankRepository clickRankRepository;
    private final OrderRankRepository orderRankRepository;
    private final ProductRepository productRepository;

    /**
     * 1. sorted Set 캐시를 초기화 시킨다
     * 2. 데이터를 mongoDB에 저장한다 (2시간전에서 현재까지 데이터 모은거를 저장)
     * 3. 2시간 마다 그러면 캐시에 있는 데이터를 초기화시킨다 그리고 다시 호출한다
     */
//    @Scheduled(cron = "0 55 0/1 * * *")
//    @Scheduled(cron = "0 0/2 * * * *") // 초 분 시 일 월 요일
    public void rankSchedule() {
        log.info("rank save schedule start");

        // cache시간 맞추기 1시간동안 캐시된 데이터를 보여주기위해
        rankService.rankCacheEvict();
        rankService.orderRankTop10();
        rankService.clickRankTop10();

        rankService.resetRankData();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));// 년 월 일 시
        // click 이전 cache된 데이터 저장
        List<ClickRank> saveClickList = rankService.clickRankTop10().stream()
                .map(c -> ClickRank.of(c, time)).collect(Collectors.toList());
        clickRankRepository.saveAll(saveClickList);
        // order 이전 cache된 데이터 저장
        List<OrderRank> saveOrderList = rankService.orderRankTop10().stream().
                map(o -> OrderRank.of(o, time)).collect(Collectors.toList());
        orderRankRepository.saveAll(saveOrderList);

        log.info("rank save schedule end");
    }


//    @Transactional
//    @Scheduled(cron = "0 0/5 * * * *")
    public void likeUpdateSchedule() {
        log.info("like update schedule start");
        List<Product> products = productRepository.findAllByList(likesDao.getLikes());
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            long likeCount = likesDao.getLikeQuantity(product.getId().toString());
            product.updateLikes(likeCount);
        }
        log.info("like update schedule end");
    }


}
