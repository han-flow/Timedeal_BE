package com.timedeal.global.dto;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    // 요청 성공
    SUCCESS(true, 1000, "요청이 성공하였습니다.", 200),



    // 회원가입 관련
    USER_ALREADY_EXISTS(false,2101, "이미 가입된 이메일입니다. 다른 방법으로 로그인하세요.", 409),
    USER_PASSWORD_INVALID(false,2102, "비밀번호는 8~15자 이내로 숫자와 소문자를 포함해야 합니다.", 400),
    USER_NICKNAME_DUPLICATE(false,2103, "이미 사용 중인 닉네임입니다.", 409),
    USER_RECOVERY_REQUIRED(false,2104, "이 계정은 삭제되었습니다. 로그인 하셔서 계정 복구하시길 바랍니다", 403),
    USER_NOT_FOUND(false,2105, "사용자를 찾을 수 없습니다.", 404),
    TEMP_USER_NOT_FOUND(false,2106, "임시 사용자 정보를 찾을 수 없습니다.", 404),
    USER_NICKNAME_NOT_VERIFIED(false,2107, "닉네임 중복 검사를 먼저 진행하세요.", 400),
    USER_TEMP_SESSION_EXPIRED(false,2108, "임시 세션이 만료되었습니다. 다시 소셜 로그인을 진행하세요.", 400),
    USER_SOCIAL_SIGNUP_REQUIRED(false,2109, "추가 정보 입력이 필요합니다. 닉네임과 비밀번호를 설정해주세요.", 401),
    USER_PASSWORD_NOT_VALID(false,2110, "비밀번호 형식이 올바르지 않습니다.", 400),



    // 실패
    VALIDATION_FAILED(false,40000, "입력 값이 유효하지 않습니다", 400),
    AUTH_REQUEST_BODY_INVALID(false,40001, "잘못된 요청 본문입니다.", 400),



    ;




    private final boolean isSuccess;
    private final int code;
    private final String message;
    private final int httpStatus;

    BaseResponseStatus(boolean isSuccess , int code, String message, int httpStatus) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
