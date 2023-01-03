package com.kurkus.kusinsa.dto.user;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.utils.PasswordEncoder;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SignupRequestDto {


    @NotNull(message = "이메일은 필수 입력값입니다")
    @Email(message = "이메일 형식이 아닙니다",
            regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Length(min = 4, max = 16, message = "비밀번호는 4글자 이상 16자 이하입니다")
    private String password;

    @NotNull(message = "핸드폰 번호는 필수 입력값입니다")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 형식이 아닙니다")
    private String phoneNumber;

    @NotNull(message = "주소를 입력해주세요")
    private String address;

    @NotNull(message = "이름을 입력해주세요")
    private String name;

    public static SignupRequestDto encryptPasswordDto(SignupRequestDto signUpDto) {
        return SignupRequestDto.builder()
                .email(signUpDto.getEmail())
                .password(PasswordEncoder.encrypt(signUpDto.getPassword()))
                .phoneNumber(signUpDto.getPhoneNumber())
                .address(signUpDto.getAddress())
                .name(signUpDto.name)
                .build();
    }

    public User toUser(){
        return User.builder()
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .address(address)
                .name(name)
                .build();
    }
}
