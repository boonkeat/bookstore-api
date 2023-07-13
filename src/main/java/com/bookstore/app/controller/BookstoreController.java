package com.bookstore.app.controller;

import com.bookstore.app.dto.BookDto;
import com.bookstore.app.dto.BookInDto;
import com.bookstore.app.model.Book;
import com.bookstore.app.repository.BookstoreRepository;
import com.bookstore.app.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/bookstore/book",
	consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE}, 
	produces = MediaType.APPLICATION_JSON_VALUE)
public class BookstoreController {

	@Autowired
	private BookstoreService bookstoreService;

	@Autowired
	private BookstoreRepository bookstoreRepository;

	@PostMapping("/retrieve")
	public ResponseEntity<List<BookDto>> retrieve(@RequestBody BookInDto bookInDto) {
		if(null == bookInDto && CollectionUtils.isEmpty(bookInDto.getAuthors()) && !StringUtils.hasText(bookInDto.getTitle())) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		System.out.println("Book search parameters ---> " + bookInDto);
		List<BookDto> bookDtoList = bookstoreService.searchBook(bookInDto);
		if(!CollectionUtils.isEmpty(bookDtoList)) {
			System.out.println("Number of Books retrieved ---> " + bookDtoList.size());
			return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Long> add(@Valid @RequestBody BookDto bookDto) {
		System.out.println("Book add parameters ---> " + bookDto);
		Book book = bookstoreService.addNewBook(bookDto);
		if(null != book && null != book.getId()) {
			System.out.println("Book added successfully ---> " + book.getId());
			return new ResponseEntity<>(book.getId(), HttpStatus.OK);
		} else {
			System.out.println("Error adding book ---> " + book.getId());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<Long> update(@PathVariable("id") Long id,  @Valid @RequestBody BookDto bookDto) {
		System.out.println("Book update parameters ---> " + bookDto);
		Optional<Book> updateBook = bookstoreRepository.findById(id);
		return (ResponseEntity<Long>) updateBook.map(book -> {
			book = bookstoreService.updateBook(book, bookDto);
			return new ResponseEntity<>(book.getId(), HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Long> update(@PathVariable("id") Long id) {
		System.out.println("Book ID to delete ---> " + id);
		Optional<Book> deleteBook = bookstoreRepository.findById(id);
		return (ResponseEntity<Long>) deleteBook.map(book -> {
			bookstoreService.deleteBook(book);
			return new ResponseEntity<>(book.getId(), HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
