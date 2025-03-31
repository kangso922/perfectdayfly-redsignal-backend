package com.example.perfectdayfly.redsignal.common.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	/*
	 * 401 UNAUTHORIZED: 인증 실패
	 */
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH-401-001",
		Map.of(
			"ko", "인증이 필요합니다.",
			"en", "Authentication required.",
			"ja", "인증이 필요합니다.",
			"zh", "인증이 필요합니다."
		)),
	INVALID_API_KEY(HttpStatus.UNAUTHORIZED, "AUTH-401-002",
		Map.of(
			"ko", "유효하지 않은 API 키입니다.",
			"en", "Invalid API key.",
			"ja", "유효하지 않은 API 키입니다.",
			"zh", "유효하지 않은 API 키입니다."
		)),
	/*
	 * 403 FORBIDDEN: 권한 없음
	 */
	USER_FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH-403-001",
		Map.of(
			"ko", "해당 요청을 수행할 권한이 없습니다.",
			"en", "You do not have permission to perform this request.",
			"ja", "해당 요청을 수행할 권한이 없습니다.",
			"zh", "해당 요청을 수행할 권한이 없습니다."
		)),

	/*
	 * 404 NOT_FOUND: 데이터 없음
	 */
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-404-001",
		Map.of(
			"ko", "사용자를 찾을 수 없습니다.",
			"en", "사용자를 찾을 수 없습니다.",
			"ja", "사용자를 찾을 수 없습니다.",
			"zh", "사용자를 찾을 수 없습니다."
		)),
	DIAGNOSIS_NOT_FOUND(HttpStatus.NOT_FOUND, "DIAGNOSIS-404-001",
		Map.of(
			"ko", "해당 진단 기록을 찾을 수 없습니다.",
			"en", "해당 진단 기록을 찾을 수 없습니다.",
			"ja", "해당 진단 기록을 찾을 수 없습니다.",
			"zh", "해당 진단 기록을 찾을 수 없습니다."
		)),
	PUSH_NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "PUSH-404-001",
		Map.of(
			"ko", "푸시 알림 설정을 찾을 수 없습니다.",
			"en", "푸시 알림 설정을 찾을 수 없습니다.",
			"ja", "푸시 알림 설정을 찾을 수 없습니다.",
			"zh", "푸시 알림 설정을 찾을 수 없습니다."
		)),

	/*
	 * 409 CONFLICT: 중복 데이터 에러
	 */
	DUPLICATE_DIAGNOSIS(HttpStatus.CONFLICT, "DIAGNOSIS-409-001",
		Map.of(
			"ko", "이미 존재하는 진단 기록입니다.",
			"en", "Diagnosis record already exists.",
			"ja", "이미 존재하는 진단 기록입니다.",
			"zh", "이미 존재하는 진단 기록입니다."
		)),
	DUPLICATE_STAGE(HttpStatus.CONFLICT, "DIAGNOSIS-409-002",
		Map.of(
			"ko", "이미 존재하는 진단 단계입니다.",
			"en", "Diagnostic stage already exists.",
			"ja", "이미 존재하는 진단 단계입니다.",
			"zh", "이미 존재하는 진단 단계입니다."
		)),
	INVALID_STAGE_ORDER(HttpStatus.CONFLICT, "DIAGNOSIS-409-003",
		Map.of(
			"ko", "요청한 진단 단계 순서가 올바르지 않습니다.",
			"en", "Invalid diagnostic stage order requested.",
			"ja", "요청한 진단 단계 순서가 올바르지 않습니다.",
			"zh", "요청한 진단 단계 순서가 올바르지 않습니다."
		)),

	/*
	 * 500 INTERNAL_SERVER_ERROR: 서버 오류
	 */
	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-500-002",
		Map.of(
			"ko", "내부 서버 오류입니다. 관리자에게 문의하세요.",
			"en", "Internal server error. Please contact the administrator.",
			"ja", "내부 서버 오류입니다. 관리자에게 문의하세요.",
			"zh", "내부 서버 오류입니다. 관리자에게 문의하세요."
		));

	private final HttpStatus status;
	private final String code;
	private final Map<String, String> messages;

	public String getMessage(String lang) {
		return messages.getOrDefault(lang, messages.get("ko")); // 기본값은 한국
	}
}
