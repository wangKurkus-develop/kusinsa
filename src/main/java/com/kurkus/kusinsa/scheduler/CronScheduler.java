package com.kurkus.kusinsa.scheduler;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.dto.response.rank.ClickRankResponseV1;
import com.kurkus.kusinsa.dto.response.rank.OrderRankResponse;
import com.kurkus.kusinsa.entity.documents.ClickRank;
import com.kurkus.kusinsa.entity.documents.OrderRank;
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
    private final ClickRankRepository clickRankRepository;
    private final OrderRankRepository orderRankRepository;

    /**
     * 1. sorted Set 캐시를 초기화 시킨다
     * 2. 데이터를 mongoDB에 저장한다
     * 3. 3시간 마다 그러면 캐시에 있는 데이터를 초기화시킨다 그리고 다시 호출한다
     */
//    @Scheduled(cron = "0 0 0/3 * * *")
//    @Scheduled(cron = "0 0/5 * * * *") // 초 분 시 일 월 요일
    public void myMethod(){
        log.info("스케줄러 시작");
        
        rankService.resetRankData();

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));// 년 월 일 시
        // click
        List<ClickRank> saveClickList = rankService.clickRankTop10().stream()
                .map(c -> ClickRank.of(c, time)).collect(Collectors.toList());
        clickRankRepository.saveAll(saveClickList);
        // order
        List<OrderRank> saveOrderList = rankService.orderRankTop10().stream().
                map(o -> OrderRank.of(o, time)).collect(Collectors.toList());
        orderRankRepository.saveAll(saveOrderList);

        log.info("스케줄러 끝");
    }


}
