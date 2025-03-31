package com.example.perfectdayfly.redsignal.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.perfectdayfly.redsignal.common.exception.ApiResponse;
import com.example.perfectdayfly.redsignal.model.dto.request.DiagnosticCreateRequest;
import com.example.perfectdayfly.redsignal.model.dto.response.DiagnosticResponseDto;
import com.example.perfectdayfly.redsignal.service.DiagnosticService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "진단")
@RestController
@RequestMapping(value = "/api/v1/diagnostic")
@RequiredArgsConstructor
public class DiagnosticController {
	private final DiagnosticService diagnosticService;

	@Operation(summary = "진단 생성")
	@PostMapping
	public ResponseEntity<ApiResponse<DiagnosticResponseDto>> creatediagnostic(
		@Valid @RequestBody DiagnosticCreateRequest requestDto) {
		DiagnosticResponseDto responseDto = diagnosticService.savediagnostic(requestDto);

		return ResponseEntity.ok(ApiResponse.success(responseDto));
	}

	@Operation(summary = "진단 삭제")
	@DeleteMapping("/{diagnosticId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponse<Void>> deletediagnostic(
		@NotNull @PathVariable("diagnosticId") UUID diagnosticId) {
		diagnosticService.deletediagnostic(diagnosticId);
		return ResponseEntity.ok(ApiResponse.success());
	}

}
