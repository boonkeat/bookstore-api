package com.bookstore.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bookstore.app.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	@Query("select new com.bookstore.app.model.User(id, username, password, isAdmin)" +
	         " from User u where u.username = ?1 and u.password = ?2 LIMIT 1")
	User login(String username, String password);
}
