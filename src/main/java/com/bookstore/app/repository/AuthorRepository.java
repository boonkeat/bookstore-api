package com.bookstore.app.repository;

import com.bookstore.app.dto.AuthorDto;
import com.bookstore.app.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
	@Query("select a from Author a where a.name in (?1)")
	List<Author> findByAuthors(List<String> authors);

	@Query("select a from Author a where a.birthday = ?1 and a.name = ?2 ")
	Author findByBirthdayAndName(Date birthday, String name);

	@Query("SELECT new com.bookstore.app.dto.AuthorDto(a.name, a.birthday) " +
			"FROM Book b, AuthorBook ab, Author a where b.id = ab.bookId and a.id = ab.authorId and b.id = ?1")
	List<AuthorDto> findAuthorsByBookId(Long bookId);
}
