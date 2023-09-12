package com.login.practice.signUp.repository;

import com.login.practice.signUp.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    Token findByToken(String token);

}
