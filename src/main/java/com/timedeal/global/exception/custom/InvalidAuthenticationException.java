package com.timedeal.global.exception.custom;

import com.timedeal.global.dto.BaseResponseStatus;
import lombok.Getter;

@Getter
public class InvalidAuthenticationException extends InvalidCustomException {

    public InvalidAuthenticationException(BaseResponseStatus status) {
        super(status);
    }
}
