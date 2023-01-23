package com.kurkus.kusinsa.service;


import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.PointMessages.*;

import com.kurkus.kusinsa.dto.request.user.LoginRequest;
import com.kurkus.kusinsa.dto.request.user.SignupRequest;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.events.point.PointLoginSavedEvent;
import com.kurkus.kusinsa.exception.user.UserException;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final SessionLoginService sessionLoginService;
    private final ApplicationEventPublisher publisher;

    /**
     * 1. 이메일 중복 확인
     * 2. 없으면 비밀번호 암호화시키기
     * 3. 저장하기
     * v2 : 환영 이메일 보내기
     */
    @Transactional
    public void signup(SignupRequest requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new UserException(EXISTS_EMAIL, HttpStatus.BAD_REQUEST);
        }
        requestDto.encryptPassword();
        userRepository.save(requestDto.toUser());
    }

    /**
     * 아이디랑 비밀번호 확인
     * 2023-01-14 : evnet 추가
     */
    @Transactional(readOnly = true)
    public void login(LoginRequest requestDto) {
        User user = userRepository.getByEmail(requestDto.getEmail());

        if(!PasswordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new UserException(AGAIN_PASSWORD_CHECK, HttpStatus.BAD_REQUEST);
        }

        sessionLoginService.login(user);
        publisher.publishEvent(new PointLoginSavedEvent(user.getId()));
    }

    @Transactional
    public void delete(Long userId){
        User user = userRepository.getById(userId);
        user.delete();
    }
}
