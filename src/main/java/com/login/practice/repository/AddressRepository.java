package com.login.practice.repository;

import com.login.practice.Entity.Address;
import com.login.practice.signUp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {

    Address findByUserId(Long user_id);


}
