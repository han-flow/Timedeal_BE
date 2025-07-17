package com.timedeal.global.exception.custom;

import com.timedeal.global.dto.BaseResponseStatus;
import lombok.Getter;

@Getter
public class InvalidCustomException extends RuntimeException {
    private final BaseResponseStatus status;

    public InvalidCustomException(BaseResponseStatus status) {
        super(status.getDetail());
        this.status = status;
    }

    public InvalidCustomException(BaseResponseStatus status, String detailMessage) {
        super(detailMessage);
        this.status = status;
    }
}