package com.example.perfectdayfly.redsignal.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.perfectdayfly.redsignal.model.entity.Diagnostic;

public interface DiagnosticRepository extends JpaRepository<Diagnostic, UUID> {
	Optional<Diagnostic> findBydiagnosticId(UUID diagnosticId);
}
