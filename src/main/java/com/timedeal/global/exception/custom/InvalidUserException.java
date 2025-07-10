package com.timedeal.global.exception.custom;

import com.timedeal.global.dto.BaseResponseStatus;

public class InvalidUserException extends InvalidCustomException{
	public InvalidUserException(BaseResponseStatus status) {
		super(status);
	}
}