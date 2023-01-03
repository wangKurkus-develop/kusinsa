package com.kurkus.kusinsa.service;


import com.kurkus.kusinsa.dto.user.LoginRequestDto;
import com.kurkus.kusinsa.dto.user.SignupRequestDto;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.exception.UserException;
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
    public void signup(SignupRequestDto requestDto) {
        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new UserException("이미 존재하는 이메일입니다", HttpStatus.BAD_REQUEST);
        }

        requestDto.encryptPassword();
        userRepository.save(requestDto.toUser());
    }

    /**
     * 아이디랑 비밀번호 확인
     */
    @Transactional(readOnly = true)
    public void login(LoginRequestDto requestDto) {
        // 없다면 예외 안그러면 user사용
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new UserException("아이디를 다시확인해주세요", HttpStatus.BAD_REQUEST)
        );
        // 비밀번호 일치하지않는다면 예외
        if(!PasswordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new UserException("비밀번호를 다시확인해주세요", HttpStatus.BAD_REQUEST);
        }

        sessionLoginService.login(user);
    }
}
