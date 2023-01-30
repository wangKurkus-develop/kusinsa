package com.kurkus.kusinsa.exception.notification;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

import com.kurkus.kusinsa.exception.CustomNotFoundException;

public class NotificationGroupNotFoundException extends CustomNotFoundException {

    public NotificationGroupNotFoundException() {
        super(NOT_FOUND_NOTIFICATION_GROUP);
    }
}
