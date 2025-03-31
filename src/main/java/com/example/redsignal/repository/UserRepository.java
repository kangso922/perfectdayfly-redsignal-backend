package com.example.perfectdayfly.redsignal.repository;

import com.example.perfectdayfly.redsignal.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findBySocialLoginId(String socialLoginId);
}
