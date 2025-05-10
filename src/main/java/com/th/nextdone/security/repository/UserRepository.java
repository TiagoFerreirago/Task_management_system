package com.th.nextdone.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.th.nextdone.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
