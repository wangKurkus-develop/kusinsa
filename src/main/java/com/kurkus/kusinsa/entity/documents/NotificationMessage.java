package com.kurkus.kusinsa.entity.documents;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "notification_message")
@Getter
@NoArgsConstructor
public class NotificationMessage {

    @Id
    private String id;
    private Long userId;
    private String message;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("expired_at")
    private LocalDateTime expiredAt;

    public NotificationMessage( Long userId, String message) {
        this.userId = userId;
        this.message = message;
        this.createdAt = LocalDateTime.now().plusHours(9);
        this.expiredAt = LocalDateTime.now().plusHours(9).plusDays(30);
    }
}
