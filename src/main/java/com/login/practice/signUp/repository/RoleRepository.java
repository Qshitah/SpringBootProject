package com.login.practice.signUp.repository;

import com.login.practice.signUp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
