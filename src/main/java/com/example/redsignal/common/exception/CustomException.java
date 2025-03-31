package com.example.perfectdayfly.redsignal.common.exception;

import lombok.Getter;

@Getter

public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String detailMessage; // 추가 메시지

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage("ko"));
		this.errorCode = errorCode;
		this.detailMessage = null;
	}

	public CustomException(ErrorCode errorCode, String detailMessage) {
		super(errorCode.getMessage("ko") + " - " + detailMessage);
		this.errorCode = errorCode;
		this.detailMessage = detailMessage;
	}

	public String getMessage(String lang) {
		String baseMessage = errorCode.getMessage(lang);
		return detailMessage != null ? baseMessage + " - " + detailMessage : baseMessage;
	}
}
