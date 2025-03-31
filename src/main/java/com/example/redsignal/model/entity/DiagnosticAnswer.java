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
@Table(name = "tb_diagnostic_answer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DiagnosticAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long diagnosticAnswerId;

	@ManyToOne
	@JoinColumn(name = "diagnostic_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Diagnostic diagnostic;

	@ManyToOne
	@JoinColumn(name = "diagnostic_stage_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DiagnosticStage diagnosticStage;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@JdbcTypeCode(SqlTypes.ARRAY)
	@Column(name = "selected_choice", columnDefinition = "TEXT[]")
	private List<String> selectedChoice;

	@CreationTimestamp
	@Column(name = "insert_dt", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime insertDt;
}
