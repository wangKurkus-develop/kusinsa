package com.kurkus.kusinsa.utils.constants;

import java.time.Duration;

public final class RedisCacheConstants {

    public static final String ORDER_RANK = "ORDER_RANK";
    public static final String CLICK_RANK = "CLICK_RANK";

    public static final Duration DEFAULT_EXPIRE_TIME_MIN = Duration.ofMinutes(1L);
    public static final Duration RANK_EXPIRE_TIME_MIN = Duration.ofMinutes(8L); // 5분
}
