package com.example.perfectdayfly.redsignal.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.perfectdayfly.redsignal.common.exception.CustomException;
import com.example.perfectdayfly.redsignal.common.exception.ErrorCode;
import com.example.perfectdayfly.redsignal.model.dto.request.DiagnosticCreateRequest;
import com.example.perfectdayfly.redsignal.model.dto.response.DiagnosticResponseDto;
import com.example.perfectdayfly.redsignal.model.entity.Diagnostic;
import com.example.perfectdayfly.redsignal.model.entity.DiagnosticAnswer;
import com.example.perfectdayfly.redsignal.model.entity.DiagnosticStage;
import com.example.perfectdayfly.redsignal.model.entity.User;
import com.example.perfectdayfly.redsignal.repository.DiagnosticAnswerRepository;
import com.example.perfectdayfly.redsignal.repository.DiagnosticRepository;
import com.example.perfectdayfly.redsignal.repository.DiagnosticStageRepository;
import com.example.perfectdayfly.redsignal.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiagnosticService {

	private final DiagnosticRepository diagnosticRepository;
	private final UserRepository userRepository;
	private final DiagnosticStageRepository diagnosticStageRepository;
	private final DiagnosticAnswerRepository diagnosticAnswerRepository;

	/**
	 * 진단 생성
	 * **/
	public DiagnosticResponseDto savediagnostic(DiagnosticCreateRequest requestDto) {

		// TODO: 사용자 관련 로직 미구현
		//  - 토큰 기반 계정 및 권한 조회 (현재는 하드코딩된 socialLoginId 사용 중)
		//  - 구독 모델에 따른 알림 개수 제한
		User user = findUserBySocialLoginId("test_google_id2");

		Diagnostic diagnostic = diagnosticRepository.findById(requestDto.getDiagnosticId())
			.orElse(null);

		int stageOrder = requestDto.getStageOrder();
		//진단 기록 생성

		if (stageOrder == 1) {
			//UUID 중복 검사
			if (diagnosticRepository.existsById(requestDto.getDiagnosticId())) {
				throw new CustomException(ErrorCode.DUPLICATE_DIAGNOSIS, "진단 ID : " + requestDto.getDiagnosticId());
			}

			diagnostic = Diagnostic.builder()
				.user(user)
				.diagnosticId(requestDto.getDiagnosticId())
				.diagnosticType(requestDto.getDiagnosticType())
				.build();

			diagnostic = diagnosticRepository.save(diagnostic);
		} else {

			//진단 정보 조회
			if (diagnostic == null) {
				throw new CustomException(ErrorCode.DIAGNOSIS_NOT_FOUND, "진단 ID : " + requestDto.getDiagnosticId());
			}

			//진단 단계 조회
			boolean stageExists = diagnosticStageRepository.existsByDiagnosticAndStageOrder(diagnostic, stageOrder);
			if (stageExists) {
				throw new CustomException(ErrorCode.DIAGNOSIS_NOT_FOUND);
			}

			int expecteNextStage = diagnosticStageRepository.findMaxStageOrderByDiagnostic(diagnostic) + 1;
			if (stageOrder != expecteNextStage) {
				throw new CustomException(ErrorCode.INVALID_STAGE_ORDER);
			}

		}
		//진단 단계 생성
		DiagnosticStage diagnosticStage = DiagnosticStage.builder()
			.diagnostic(diagnostic)
			.stageOrder(1)
			.symptomChoices(null)
			.diseaseChoices(null)
			.build();

		diagnosticStage = diagnosticStageRepository.save(diagnosticStage);

		DiagnosticAnswer diagnosticAnswer = DiagnosticAnswer.builder()
			.user(user)
			.diagnostic(diagnostic)
			.diagnosticStage(diagnosticStage)
			.selectedChoice(requestDto.getQuestion())
			.build();

		diagnosticAnswer = diagnosticAnswerRepository.save(diagnosticAnswer);

		// **AI 응답값은 현재 하드코딩 (추후 제미나이 연동)**
		List<String> aidDisease = List.of("소화불량", "소화불량");  // TODO: AI 연동 후 수정
		List<String> aiSymptom = List.of("복통", "속 쓰림");      // TODO: AI 연동 후 수정


		/*// AI 응답값 적용

		TODO::엔티티에 update함수 만들기
			boolean needsUpdate = false;
			if (aiDisease != null && !aiDisease.isEmpty()) {
				diagnosticStage.setDiseaseChoices(aiDisease);
				needsUpdate = true;
			}
			if (aiSymptom != null && !aiSymptom.isEmpty()) {
				diagnosticStage.setSymptomChoices(aiSymptom);
				needsUpdate = true;
			}
			if (needsUpdate) {
				diagnosticStageRepository.save(diagnosticStage);
			}
		*/

		return DiagnosticResponseDto.builder()
			.timestamp(diagnostic.getInsertDt().toInstant().toEpochMilli())
			.question(requestDto.getQuestion())
			.disease(diagnosticStage.getDiseaseChoices())
			.symptom(diagnosticStage.getSymptomChoices())
			.build();

	}

	/**
	 * 진단 삭제
	 * **/

	@Transactional
	public void deletediagnostic(UUID diagnosticId) {

		// TODO: 사용자 관련 로직 미구현
		//  - 토큰 기반 계정 및 권한 조회 (현재는 하드코딩된 socialLoginId 사용 중)
		//  - 구독 모델에 따른 알림 개수 제한
		User user = findUserBySocialLoginId("test_google_id2");

		Diagnostic diagnostic = finddiagnosticById(diagnosticId);

		validateUserAccess(user, diagnostic);

		// 진단 기록 삭제
		diagnosticRepository.deleteById(diagnosticId);

	}

	/**
	 * 유저 조회
	 */
	private User findUserBySocialLoginId(String socialLoginId) {
		return userRepository.findBySocialLoginId(socialLoginId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	/**
	 * 진단 조회
	 */
	private Diagnostic finddiagnosticById(UUID diagnosticId) {
		return diagnosticRepository.findById(diagnosticId)
			.orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSIS_NOT_FOUND, "진단 ID : " + diagnosticId));
	}

	/**
	 * 유저 권한 검증 (본인의 진단 기록만 삭제 가능)
	 */
	private void validateUserAccess(User user, Diagnostic diagnostic) {
		if (!diagnostic.getUser().getUserId().equals(user.getUserId())) {
			throw new CustomException(ErrorCode.USER_FORBIDDEN,
				String.format("유저 ID: %s, 진단 ID: %s.",
					user.getUserId(), diagnostic.getDiagnosticId()));
		}
	}
}
