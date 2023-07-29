package com.bookstore.app.repository;

import com.bookstore.app.dto.BookDto;
import com.bookstore.app.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookstoreRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
	@Query("select new com.bookstore.app.dto.BookDto(b.id, b.isbn, b.title, b.year, b.price, b.genre)" +
			" from Book b join b.authors a where b.title = ?1 and a.name in (?2)")
	List<BookDto> findBookByTitleAuthors(String title, List<String> authors);

	@Query("select new com.bookstore.app.dto.BookDto(b.id, b.isbn, b.title, b.year, b.price, b.genre)" +
			" from Book b join b.authors a where a.name in (?1)")
	List<BookDto> findBookByAuthors(List<String> authors);

	@Query("SELECT new com.bookstore.app.dto.BookDto(b.id, b.isbn, b.title, b.year, b.price, b.genre) " +
			"FROM Book b, AuthorBook ab, Author a where b.id = ab.bookId and a.id = ab.authorId and b.title = ?1 and a.name = ?2")
	List<BookDto> findBookByTitleAuthor(String title, String author);

	@Query("SELECT new com.bookstore.app.dto.BookDto(b.id, b.isbn, b.title, b.year, b.price, b.genre) " +
			"FROM Book b, AuthorBook ab, Author a where b.id = ab.bookId and a.id = ab.authorId and a.name = ?1")
	List<BookDto> findBookByAuthor(String author);

	@Query("SELECT new com.bookstore.app.dto.BookDto(b.id, b.isbn, b.title, b.year, b.price, b.genre) " +
			"FROM Book b where b.title = ?1")
	List<BookDto> findBookByTitle(String title);

	@Query("select b from Book b join b.authors a where b.title = ?1 and b.isbn = ?2 and b.year = ?3 " +
			"and b.genre = ?4 and a.name in (?5)")
	Book findBookByTitleIsbnYearAuthors(String title, String isbn, int year, String genre, List<String> authors);
}
