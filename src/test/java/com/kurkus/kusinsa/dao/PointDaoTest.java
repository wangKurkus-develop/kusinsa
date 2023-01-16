package com.kurkus.kusinsa.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PointDaoTest {

    @Autowired
    PointDao pointDao;

    @Test
    void checkTodayLoginPoint() {
        Long user1 = 20L;
        Long user2 = 30L;

        pointDao.checkTodayLoginPoint(user1);
        pointDao.checkTodayLoginPoint(user2);
        pointDao.checkTodayLoginPoint(user1);




    }
}