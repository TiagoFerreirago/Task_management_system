package com.th.nextdone.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.th.nextdone.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.username =:username")
	User findByName(@Param("username")String username);
	
	boolean existsByUsername(String username);
}
