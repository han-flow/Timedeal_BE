package com.timedeal.global.dto;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    // 요청 성공
    SUCCESS(true, 1000, "요청이 성공하였습니다.", 200),

    // 실패
    VALIDATION_FAILED(false,40000, "입력 값이 유효하지 않습니다", 400),
    AUTH_REQUEST_BODY_INVALID(false,40001, "잘못된 요청 본문입니다.", 400);


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
