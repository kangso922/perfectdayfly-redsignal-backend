package com.example.perfectdayfly.redsignal.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.perfectdayfly.redsignal.model.enums.AiStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_ai_log")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long aiLogId;

	@Column(name = "ai_request", nullable = false)
	private String aiRequest;

	@Column(name = "ai_response", nullable = false)
	private String aiResponse;

	@Column(name = "ai_status", nullable = false)
	private AiStatus aiStatus;

	@UpdateTimestamp
	private LocalDateTime updateDt;

	@CreationTimestamp
	@Column(name = "insert_dt", nullable = false)
	private LocalDateTime insertDt;
}
