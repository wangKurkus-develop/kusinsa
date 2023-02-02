package com.kurkus.kusinsa.controller;


import javax.validation.Valid;

import com.kurkus.kusinsa.annotation.LoginCheck;
import com.kurkus.kusinsa.dto.request.user.DeviceCreateRequest;
import com.kurkus.kusinsa.dto.request.user.LoginRequest;
import com.kurkus.kusinsa.dto.request.user.SignupRequest;
import com.kurkus.kusinsa.service.user.DeviceService;
import com.kurkus.kusinsa.service.user.SessionLoginService;
import com.kurkus.kusinsa.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SessionLoginService sessionLoginService;
    private final DeviceService deviceService;


    @PostMapping("/signup")
    public ResponseEntity<Void> signupUser(@Valid @RequestBody SignupRequest requestDto){
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest requestDto){
        userService.login(requestDto);
        return ResponseEntity.ok().build();
    }


    @LoginCheck
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(){
        sessionLoginService.logout();
        return ResponseEntity.ok().build();
    }


    @PostMapping("/devices")
    public ResponseEntity<Void> deviceSave(@RequestBody DeviceCreateRequest request){
        deviceService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
