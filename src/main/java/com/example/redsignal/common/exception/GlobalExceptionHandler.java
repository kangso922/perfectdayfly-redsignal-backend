package com.example.perfectdayfly.redsignal.common.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로깅
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	//private final UserService userService; // 추후 사용자 언어 설정이 필요하면 연결

	/**
	 *  Valid 유효성 검증 실패 시 발생하는 예외 처리
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage(); // 첫 번째 에러 메시지
		log.warn("[Validation Error] {}", message); // 로그 추가
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.error("INVALID_INPUT_VALUE", message));
	}

	/**
	 *  UUID 형식 오류 처리
	 */
	@ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
	public ResponseEntity<ApiResponse<Void>> handleUUIDFormatException(Exception ex) {
		log.warn("[UUID Format Error] {}", ex.getMessage());
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.error("INVALID_UUID_FORMAT", "잘못된 UUID 형식입니다."));
	}

	/**
	 * 지원되지 않는 HTTP 메서드 요청 시 발생하는 예외 처리
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception) {
		log.warn("[Method Not Allowed] {}", exception.getMessage()); // 로그 추가

		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.body(ApiResponse.error("METHOD_NOT_ALLOWED", "지원되지 않는 요청 방식입니다."));
	}

	/**
	 * 존재하지 않는 API 엔드포인트 요청 시 발생하는 예외 처리 (404)
	 */
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException exception) {
		log.warn("[Not Found] 요청한 리소스를 찾을 수 없음: {}", exception.getMessage()); // 로그 추가

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(ApiResponse.error("NOT_FOUND", "요청한 API 엔드포인트를 찾을 수 없습니다."));
	}

	/**
	 * 비즈니스 로직에서 발생하는 CustomException 처리
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
		String userLang = "ko"; // 기본 언어 설정, 추후 user_language 가져오기
		String baseMessage = ex.getErrorCode().getMessage(userLang);

		// 추가적인 상세 메시지가 있는 경우 포함
		String fullMessage = Optional.ofNullable(ex.getDetailMessage())
			.filter(detail -> !detail.isEmpty())
			.map(detail -> String.format("%s (%s)", baseMessage, detail))
			.orElse(baseMessage);

		log.warn("[Custom Exception] {}: {}", ex.getErrorCode().getCode(), fullMessage); // 로그 추가

		return ResponseEntity
			.status(ex.getErrorCode().getStatus())
			.body(ApiResponse.error(ex.getErrorCode().getCode(), fullMessage));
	}

	/**
	 * 예상하지 못한 모든 예외 처리 (500 서버 내부 오류)
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
		log.warn("[Unhandled Exception] {}", exception.getMessage(), exception); // 예외 로그 추가
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.error("INTERNAL_SERVER_ERROR", "서버 내부 오류입니다."));
	}

}
