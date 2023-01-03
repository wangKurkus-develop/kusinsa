package com.kurkus.kusinsa.controller;


import javax.validation.Valid;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.dto.user.LoginRequestDto;
import com.kurkus.kusinsa.dto.user.SignupRequestDto;
import com.kurkus.kusinsa.service.SessionLoginService;
import com.kurkus.kusinsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SessionLoginService sessionLoginService;

    /**
     * 요청으로 Valid 체크를 추가해야한다.
     * @param requestDto
     * @return Void 상태코드
     */
    @PostMapping("/signup")
    public ResponseEntity<Void> signupUser(@Valid @RequestBody SignupRequestDto requestDto){
        userService.signup(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto requestDto){
        userService.login(requestDto);
        return ResponseEntity.ok().build();
    }


    @LoginCheck
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(){
        sessionLoginService.logout();
        return ResponseEntity.ok().build();
    }



}
