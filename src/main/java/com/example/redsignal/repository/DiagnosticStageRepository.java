package com.example.perfectdayfly.redsignal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.perfectdayfly.redsignal.model.entity.Diagnostic;
import com.example.perfectdayfly.redsignal.model.entity.DiagnosticStage;

public interface DiagnosticStageRepository extends JpaRepository<DiagnosticStage, Long> {
	boolean existsByDiagnosticAndStageOrder(Diagnostic diagnostic, int stageOrder);

	@Query("SELECT COALESCE(MAX(stageOrder), 0) FROM DiagnosticStage WHERE diagnostic = :diagnostic")
	int findMaxStageOrderByDiagnostic(@Param("diagnostic") Diagnostic diagnostic);
}
