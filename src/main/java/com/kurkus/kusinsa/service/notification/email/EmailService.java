package com.kurkus.kusinsa.service.notification.email;


import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.kurkus.kusinsa.repository.mongo.NotificationMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @Value("${spring.mail.username}")
    private String id;

    private final JavaMailSender javaMailSender;


    @Transactional
    public String sendDeliveryComplete(String email, String productName)  {
        MimeMessage message = javaMailSender.createMimeMessage();
        String content = productName + " 배달완료 했습니다";
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("배달 완료 알림");
            mimeMessageHelper.setText(content);
            mimeMessageHelper.setFrom(new InternetAddress(id, "kusinsa_Admin"));
            javaMailSender.send(message);
        } catch (MessagingException e){
            System.out.println("메시지 못받는경우");
        } catch (UnsupportedEncodingException encodingException){
            System.out.println("인코딩");
        }
        return content;
    }

    public String sendGroup(String[] groupUser, String productName)  {

        MimeMessage message = javaMailSender.createMimeMessage();
        String content = productName + " 재입고 했습니다";
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setTo(groupUser);
            mimeMessageHelper.setSubject("상품 재입고 알림");
            mimeMessageHelper.setText(content);
            mimeMessageHelper.setFrom(new InternetAddress(id, "kusinsa_Admin"));
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("메일보내기 실패");
        }
        return content;
    }


}
