package com.bookstore.app.service;

import com.bookstore.app.dto.BookDto;
import com.bookstore.app.dto.BookInDto;
import com.bookstore.app.model.Book;

import java.util.List;

public interface BookstoreService {
	Book addNewBook(BookDto bookDto);
	
	Book updateBook(Book book, BookDto bookDto);

	List<BookDto> searchBook(BookInDto bookInDto);
	
	void deleteBook(Book book);
}
