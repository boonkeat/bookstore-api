package com.bookstore.app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bookstore.app.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	@Query("select u from User u where u.username = ?1 and u.password = ?2 ")
	User login(String username, String password);

	User findByUsername(String username);
}
