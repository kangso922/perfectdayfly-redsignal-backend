package com.example.perfectdayfly.redsignal.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final boolean success; // 요청 성공 여부
    private final String code;     // 에러 코드 (성공 시 "200")
    private final String message;  // 응답 메시지
    private final T data;          // 성공 시 데이터, 실패 시 null

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "200", "요청 성공", data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, "200", "요청 성공", null);
    }

    public static ApiResponse<Void> error(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}
