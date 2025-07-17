package com.timedeal.global.exception;

import com.timedeal.global.dto.BaseResponse;
import com.timedeal.global.dto.BaseResponseStatus;
import com.timedeal.global.dto.Empty;
import com.timedeal.global.exception.custom.InvalidCustomException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCustomException.class)
    public BaseResponse<Empty> handleGlobalException(InvalidCustomException e, HttpServletResponse response) {
        response.setStatus(e.getStatus().getHttpStatus().value());
        return BaseResponse.error(e.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return BaseResponse.error(BaseResponseStatus.VALIDATION_FAILED, errors);
    } // 요청 DTO 검증 실패 [Validation 실패]

}
