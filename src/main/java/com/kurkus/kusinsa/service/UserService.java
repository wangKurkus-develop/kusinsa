package com.kurkus.kusinsa.service;


import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

import com.kurkus.kusinsa.dto.request.user.LoginRequest;
import com.kurkus.kusinsa.dto.request.user.SignupRequest;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.exception.user.UserException;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SessionLoginService sessionLoginService;

    /**
     * 1. 이메일 중복 확인
     * 2. 없으면 비밀번호 암호화시키기
     * 3. 저장하기
     */
    @Transactional
    public void signup(SignupRequest requestDto) {
        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new UserException(EXISTS_EMAIL, HttpStatus.BAD_REQUEST);
        }
        requestDto.encryptPassword();
        userRepository.save(requestDto.toUser());
    }

    /**
     * 아이디랑 비밀번호 확인
     */
    @Transactional(readOnly = true)
    public void login(LoginRequest requestDto) {
        // 없다면 예외 안그러면 user사용
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new UserException(AGAIN_ID_CHECK, HttpStatus.BAD_REQUEST)
        );
        // 비밀번호 일치하지않는다면 예외
        if(!PasswordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new UserException(AGAIN_PASSWORD_CHECK, HttpStatus.BAD_REQUEST);
        }

        sessionLoginService.login(user);
    }
}
