package com.kurkus.kusinsa.controller.notification;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.annotation.SessionUserId;
import com.kurkus.kusinsa.dto.request.notification.NotificationCreateRequest;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping
    @LoginCheck(userType = UserType.USER)
    public ResponseEntity<Void> save(@SessionUserId Long userId, @RequestBody NotificationCreateRequest request) {
        notificationService.save(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 추가할작업
     * 알림취소
     * 알림 신청목록보기
     */



}
