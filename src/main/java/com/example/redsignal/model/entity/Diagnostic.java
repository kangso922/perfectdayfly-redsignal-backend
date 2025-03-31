package com.example.perfectdayfly.redsignal.model.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_diagnostic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnostic {

	@Id
	private UUID diagnosticId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column(name = "diagnostic_type", length = 20, nullable = false)
	private String diagnosticType;

	@CreationTimestamp
	@Column(name = "insert_dt", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime insertDt;
}
