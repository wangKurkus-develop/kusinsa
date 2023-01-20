package com.kurkus.kusinsa.utils.constants;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {
    // 회원가입
    public static final String EXISTS_EMAIL = "이미 존재하는 이메일입니다";
    // 로그인
    public static final String AGAIN_ID_CHECK = "아이디를 다시확인해주세요";
    public static final String AGAIN_PASSWORD_CHECK =  "비밀번호를 다시확인해주세요";

    // 세션 로그인
    public static final String NOT_FOUND_SESSION = "세션이 존재하지 않습니다";
    public static final String FORBIDDEN = "권한이 없습니다";

    // user
    public static final String NOT_FOUND_USER = "존재하지 않는 유저입니다";

    // product
    public static final String EXISTS_PRODUCT = "이미존재하는 상품입니다";
    public static final String NOT_FOUND_PRODUCT = "존재하지 않는 브랜드입니다";
    // category
    public static final String NOT_FOUND_CATEGORY = "존재하지 않는 카테고리입니다";
    public static final String EXISTS_CATEGORY = "이미존재하는 카테고리입니다";
    // brand
    public static final String NOT_FOUND_BRAND = "존재하지 않는 브랜드입니다";
    public static final String EXISTS_BRAND = "이미존재하는 카테고리입니다";
    // order history
    public static final String NOT_FOUND_ORDER_HISTORY = "존재하지 않는 주문 기록입니다";
    // 배송상태
    public static final String FAIL_CANCEL_DELIVERY = "주문취소는 배송준비중일떄만가능합니다";
    // 주문=결제 상태
    public static final String NOT_AUTHORITY = "환불 권한이 없습니다";


    // order
    public static final String NOT_ENOUGH_QUANTITY = "수량이 모자릅니다";
    public static final String NOT_SALE = "매진이거나 판매중이 아닙니다";

}
