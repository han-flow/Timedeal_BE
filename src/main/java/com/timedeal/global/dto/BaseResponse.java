package com.timedeal.global.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"success", "title", "httpStatus", "detail", "instance", "result"})
public class BaseResponse<T> {

    private final boolean success;
    private final String title;
    private final int httpStatus;
    private final String detail;
    private final String instance;
    private final T result;

    // 요청에 성공한 경우 - 결과 값이 없을 때
    public static BaseResponse<Empty> success() {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, Empty.getInstance());
    }

    // 요청에 성공한 경우 - 결과 값이 있을 때
    public static <T> BaseResponse<T> success(T result) {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, result);
    }

    // 요청에 실패한 경우 - 결과 값이 없을 때
    public static BaseResponse<Empty> error(BaseResponseStatus status) {
        return new BaseResponse<>(status, Empty.getInstance());
    }

    // 요청에 실패한 경우 - 추가 메시지 또는 데이터가 있는 경우
    public static <T> BaseResponse<T> error(BaseResponseStatus status, T result) {
        return new BaseResponse<>(status, result);
    }

    private BaseResponse(BaseResponseStatus status, T result) {
        this.success = status.isSuccess();
        this.title = status.getTitle();
        this.httpStatus = status.getHttpStatus().value();
        this.detail = status.getDetail();
        this.result = result;

        this.instance = getRequestUri(); // 동적으로 얻은 URI 할당
    }

    private String getRequestUri() {
        String requestUri = null;
        try {
            // HTTP 요청 컨텍스트에서만 처리
            if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes sra) {
                HttpServletRequest request = sra.getRequest();

                if (request != null) {
                    requestUri = request.getRequestURI();
                }
                // else: HttpServletRequest 객체 없음
            }
            // else: 웹 요청 컨텍스트 아님
        } catch (IllegalStateException e) {
            // 요청 컨텍스트 가져오는 중 오류 발생
            System.err.println("RequestContextHolder에서 요청 컨텍스트를 가져오는 중 오류 발생: " + e.getMessage());
        } catch (Exception e) {
            // 예상치 못한 오류 발생
            System.err.println("getRequestUri() 메서드 실행 중 예상치 못한 오류 발생: " + e.getMessage());
        }
        return requestUri;
    }
}
