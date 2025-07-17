package com.timedeal.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {

    // 요청 성공
    SUCCESS(true, "SUCCESS", HttpStatus.OK, "요청이 성공하였습니다."),

    // 회원가입 관련
    USER_ALREADY_EXISTS(false, "USER_ALREADY_EXISTS", HttpStatus.REQUEST_TIMEOUT, "이미 가입된 이메일입니다. 다른 방법으로 로그인하세요."),
    USER_PASSWORD_INVALID(false, "USER_PASSWORD_INVALID", HttpStatus.BAD_REQUEST, "비밀번호는 8~15자 이내로 숫자와 소문자를 포함해야 합니다."),
    USER_NICKNAME_DUPLICATE(false, "USER_NICKNAME_DUPLICATE", HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    USER_RECOVERY_REQUIRED(false, "USER_RECOVERY_REQUIRED", HttpStatus.FORBIDDEN, "이 계정은 삭제되었습니다. 로그인 하셔서 계정 복구하시길 바랍니다"),
    USER_NOT_FOUND(false, "USER_NOT_FOUND", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    TEMP_USER_NOT_FOUND(false, "TEMP_USER_NOT_FOUND", HttpStatus.NOT_FOUND, "임시 사용자 정보를 찾을 수 없습니다."),
    USER_NICKNAME_NOT_VERIFIED(false, "USER_NICKNAME_NOT_VERIFIED", HttpStatus.BAD_REQUEST, "닉네임 중복 검사를 먼저 진행하세요."),
    USER_TEMP_SESSION_EXPIRED(false, "USER_TEMP_SESSION_EXPIRED", HttpStatus.BAD_REQUEST, "임시 세션이 만료되었습니다. 다시 소셜 로그인을 진행하세요."),
    USER_SOCIAL_SIGNUP_REQUIRED(false, "USER_SOCIAL_SIGNUP_REQUIRED", HttpStatus.UNAUTHORIZED, "추가 정보 입력이 필요합니다. 닉네임과 비밀번호를 설정해주세요."),
    USER_PASSWORD_NOT_VALID(false, "USER_PASSWORD_NOT_VALID", HttpStatus.BAD_REQUEST, "비밀번호 형식이 올바르지 않습니다."),


    // 이메일 인증 관련
    EMAIL_REQUEST_LOCKED(false, "EMAIL_REQUEST_LOCKED", HttpStatus.BAD_REQUEST, "인증 코드는 30초 후에 다시 요청할 수 있습니다."),
    EMAIL_ALREADY_VERIFIED(false, "EMAIL_ALREADY_VERIFIED", HttpStatus.BAD_REQUEST, "이메일 인증이 이미 완료되었습니다."),
    EMAIL_VERIFICATION_EXPIRED(false, "EMAIL_VERIFICATION_EXPIRED", HttpStatus.BAD_REQUEST, "이메일 인증 시간이 만료되었습니다. 다시 인증을 진행해 주세요."),
    EMAIL_VERIFICATION_REQUIRED(false, "EMAIL_VERIFICATION_REQUIRED", HttpStatus.BAD_REQUEST, "이메일 인증이 필요합니다."),
    EMAIL_INVALID_CODE(false, "EMAIL_INVALID_CODE", HttpStatus.BAD_REQUEST, "유효하지 않은 인증 코드입니다."),
    EMAIL_SEND_FAILED(false, "EMAIL_SEND_FAILED", HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다."),



    // 실패
    VALIDATION_FAILED(false, "VALIDATION_FAILED", HttpStatus.BAD_REQUEST, "입력 값이 유효하지 않습니다."),
    AUTH_REQUEST_BODY_INVALID(false, "AUTH_REQUEST_BODY_INVALID", HttpStatus.BAD_REQUEST, "잘못된 요청 본문입니다.")



    ;



    private final boolean isSuccess;
    //private final String type;
    private final String title;
    private final HttpStatus httpStatus;
    private final String detail;



    BaseResponseStatus(boolean isSuccess, String title, HttpStatus status, String detail) {
        this.isSuccess = isSuccess;
        //this.type = type;
        this.title = title;
        this.httpStatus = status;
        this.detail = detail;
    }
}
