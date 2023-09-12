package com.login.practice.signUp.repository;

import com.login.practice.signUp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_ADMIN'")
    Set<User> findAdminUsers();

    User findByEmail(String email);
}

