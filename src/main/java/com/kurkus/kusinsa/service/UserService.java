package com.kurkus.kusinsa.service;


import com.kurkus.kusinsa.dto.user.LoginRequestDto;
import com.kurkus.kusinsa.dto.user.SignupRequestDto;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.exception.LoginException;
import com.kurkus.kusinsa.exception.SignUpException;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
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
        // 없다면
        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new SignUpException("이미 존재하는 이메일입니다");
        }

        SignupRequestDto encryptPasswordDto = SignupRequestDto.encryptPasswordDto(requestDto);
        userRepository.save(encryptPasswordDto.toUser());
    }

    /**
     * 아이디랑 비밀번호 확인
     */
    @Transactional(readOnly = true)
    public void login(LoginRequestDto requestDto) {
        // 없다면 예외 안그러면 user사용
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new LoginException("아이디나 비밀번호를 다시확인해주세요")
        );
        // 비밀번호 일치하지않는다면 예외
        if(!PasswordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new LoginException("아이디나 비밀번호를 다시확인해주세요");
        }
        sessionLoginService.login(user);
    }
}
