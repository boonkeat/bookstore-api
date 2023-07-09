package com.bookstore.app.repository;

import com.bookstore.app.dto.BookDto;
import com.bookstore.app.model.Book;
import com.bookstore.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookstoreRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
	User findUserByUsernamePassword(String username, String password);
	@Query("select new com.bookstore.app.dto.BookDto(id, isbn, title, year, price, genre)" +
			" from Book b where b.title = ?1 and b.authors.name in (?2)")
	List<BookDto> findBookByTitleAuthors(String title, List<String> authors);
}
