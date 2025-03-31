package com.example.perfectdayfly.redsignal.model.entity;

import com.example.perfectdayfly.redsignal.model.enums.LoginPlatform;
import com.example.perfectdayfly.redsignal.model.enums.LoginProvider;
import com.example.perfectdayfly.redsignal.model.enums.UserGender;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tb_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private UUID userId;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    private LocalDate userBirth;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private UserGender userGender;

    @Column(length = 50)
    private String userCountry;

    @Column(nullable = false, length = 50)
    private String userTimezone;

    @Column(nullable = false, name = "last_login")
    private LocalDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "login_platform")
    private LoginPlatform loginPlatform;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_provider", length = 10)
    private LoginProvider loginProvider;

    @Column(length = 100)
    private String socialLoginId;

    @CreationTimestamp
    @Column(name = "insert_dt", nullable = false)
    private LocalDateTime insertDt;

    @UpdateTimestamp
    private LocalDateTime updateDt;
}
