package com.example.perfectdayfly.redsignal.model.entity;

import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_diagnostic_stage")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticStage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long diagnosticStageId;

	@ManyToOne
	@JoinColumn(name = "diagnostic_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Diagnostic diagnostic;

	@Column(nullable = false)
	private Integer stageOrder;

	@JdbcTypeCode(SqlTypes.ARRAY)
	@Column(name = "symptom_choices")
	private List<String> symptomChoices;

	@JdbcTypeCode(SqlTypes.ARRAY)
	@Column(name = "disease_choices")
	private List<String> diseaseChoices;

	@CreationTimestamp
	@Column(name = "insert_dt", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime insertDt;
}
