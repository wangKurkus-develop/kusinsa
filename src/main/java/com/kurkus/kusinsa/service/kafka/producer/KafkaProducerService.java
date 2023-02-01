package com.kurkus.kusinsa.service.kafka;


import com.kurkus.kusinsa.enums.DeliveryStatus;
import com.kurkus.kusinsa.messages.DeliveryMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    





}
