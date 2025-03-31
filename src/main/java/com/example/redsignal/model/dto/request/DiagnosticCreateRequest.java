package com.example.perfectdayfly.redsignal.model.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticCreateRequest {

	@NotNull(message = "사용자 ID는 필수입니다.")
	private UUID diagnosticId;
	@NotEmpty
	private List<String> question;
	@NotBlank
	private String diagnosticType;
	@Min(1)
	private int stageOrder;
}
