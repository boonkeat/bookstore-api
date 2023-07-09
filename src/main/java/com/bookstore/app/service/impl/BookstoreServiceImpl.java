package com.bookstore.app.service.impl;

import com.bookstore.app.dto.BookDto;
import com.bookstore.app.dto.BookInDto;
import com.bookstore.app.model.Book;
import com.bookstore.app.repository.BookstoreRepository;
import com.bookstore.app.service.BookstoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class BookstoreServiceImpl implements BookstoreService {

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Book addNewBook(BookDto bookDto) {
		Book book = new Book();
		BeanUtils.copyProperties(bookDto, book);
		book = bookstoreRepository.save(book);
		return book;
	}

	@Override
	public Book updateBook(Book book, BookDto bookDto) {
		BeanUtils.copyProperties(bookDto, book);
		book = bookstoreRepository.save(book);
		return book;
	}

	@Override
	public List<BookDto> searchBook(BookInDto bookInDto) {
		List<BookDto> bookOutDtoList = bookstoreRepository.findBookByTitleAuthors(bookInDto.getTitle(), bookInDto.getAuthors());
		return bookOutDtoList;
	}

	@Override
	public void deleteBook(Book book) {
		bookstoreRepository.delete(book);
	}

	private BookstoreRepository bookstoreRepository;
	
}
