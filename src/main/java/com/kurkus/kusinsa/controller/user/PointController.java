package com.kurkus.kusinsa.controller.user;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.annotation.SessionUserId;
import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
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

    @GetMapping
    @LoginCheck(userType = UserType.USER)
    public ResponseEntity<Page<PointResponse>> findAll(@RequestBody PointSearchCondition condition,
                                                       @RequestParam(name = "page", defaultValue = "0")int page) {
        return ResponseEntity.ok(pointService.searchCondition(condition, page));
    }

}
