package com.kurkus.kusinsa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 파라미터 바인딩때 사용될것
// 유저가 매번 요청할때 유저아이디를 세션에서 가져와서 바인딩
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface SessionUserId {

}
