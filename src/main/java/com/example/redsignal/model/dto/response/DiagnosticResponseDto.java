package com.example.perfectdayfly.redsignal.model.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiagnosticResponseDto {
	private Long timestamp;
	private List<String> question;
	private List<String> disease;
	private List<String> symptom;
}
