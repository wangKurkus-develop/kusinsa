package com.kurkus.kusinsa.controller;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.annotation.SessionUserId;
import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.response.point.PointResponse;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
@Slf4j
public class PointController {

    private final PointService pointService;

//    @PostMapping
//    @LoginCheck(userType = UserType.USER)
//    public ResponseEntity<Void> save(@SessionUserId String userId, @RequestBody PointCreateRequest request){
//        pointService.save(userId, request);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @GetMapping
    @LoginCheck(userType = UserType.USER)
    public ResponseEntity<Page<PointResponse>> findAll(@SessionUserId String userId,
                                                       @RequestParam(defaultValue = "all") PointType division,
                                                       @RequestParam(name = "page") int page){
        log.info("page : {}", page);
        log.info("Point division test : {}", division);
        return ResponseEntity.ok(pointService.findAll(userId, division, page));
    }
}
