package com.kurkus.kusinsa.utils.constants;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionConstants {
    // 회원가입
    public static final String EXISTS_EMAIL = "이미 존재하는 이메일입니다";
    // 로그인
    public static final String AGAIN_ID_CHECK = "아이디를 다시확인해주세요";
    public static final String AGAIN_PASSWORD_CHECK =  "비밀번호를 다시확인해주세요";

    // 세션 로그인
    public static final String UNAUTHORIZED = "세션이 존재하지 않습니다";
    public static final String FORBIDDEN = "권한이 없습니다";

}
