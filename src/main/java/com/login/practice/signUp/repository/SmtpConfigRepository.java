package com.login.practice.signUp.repository;

import com.login.practice.signUp.entity.SmtpConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmtpConfigRepository extends JpaRepository<SmtpConfig,Long> {
}
