package com.bookstore.app.service.impl;

import com.bookstore.app.dto.AuthorDto;
import com.bookstore.app.dto.BookDto;
import com.bookstore.app.dto.BookInDto;
import com.bookstore.app.exception.RestApiException;
import com.bookstore.app.model.Author;
import com.bookstore.app.model.Book;
import com.bookstore.app.repository.AuthorRepository;
import com.bookstore.app.repository.BookstoreRepository;
import com.bookstore.app.service.BookstoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookstoreServiceImpl implements BookstoreService {

    @Autowired
    private BookstoreRepository bookstoreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Book addNewBook(BookDto bookDto) {
        Book existingBook = bookstoreRepository.findBookByTitleIsbnYearAuthors(bookDto.getTitle(), bookDto.getIsbn(),
                bookDto.getYear(), bookDto.getGenre(), bookDto.getAuthors().stream().map(AuthorDto::getName).collect(Collectors.toList()));
        if (null != existingBook) {
            throw new RestApiException("Book already exists!", HttpStatus.BAD_REQUEST);
        }
        Set<Author> authorSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(bookDto.getAuthors())) {
            authorSet = convertToAuthor(bookDto.getAuthors());
        }
        // save book
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        book.setAuthors(authorSet);
        book = bookstoreRepository.save(book);
        return book;
    }

    private Set<Author> convertToAuthor(List<AuthorDto> authorDtoList) {
        Set<Author> authorSet = authorDtoList.stream().map(author -> {
            Author existingAuthor = authorRepository.findByBirthdayAndName(author.getBirthday(), author.getName());
            if (null == existingAuthor) {
                Author newAuthor = new Author();
                BeanUtils.copyProperties(author, newAuthor);
                return newAuthor;
            }
            return existingAuthor;
        }).collect(Collectors.toSet());
        authorRepository.saveAll(authorSet);
        return authorSet;
    }

    @Override
    public Book updateBook(Book book, BookDto bookDto) {
//		Book existingBook = bookstoreRepository.findBookByTitleIsbnYearAuthors(bookDto.getTitle(), bookDto.getIsbn(), bookDto.getYear(), bookDto.getGenre(), bookDto.getAuthors().stream().map(AuthorDto::getName).collect(Collectors.toList()));
        Optional<Book> optionalBook = bookstoreRepository.findById(book.getId());
        Book existingBook;
        if (null == optionalBook || !optionalBook.isPresent()) {
            throw new RestApiException("Book not found!", HttpStatus.BAD_REQUEST);
        } else {
            existingBook = optionalBook.get();
        }
        Set<Author> existingAuthorSet = existingBook.getAuthors();
        Set<Author> updateAuthorSet = convertToAuthor(bookDto.getAuthors());
        Set<Author> deleteAuthorSet = new HashSet<>();
        Set<Author> addAuthorSet = new HashSet<>();
        deleteAuthorSet = existingAuthorSet.stream().filter(existingAuthor ->
                updateAuthorSet.stream().anyMatch(updateAuthor ->
                        !updateAuthor.getBirthday().equals(existingAuthor.getBirthday()) && !updateAuthor.getName().equalsIgnoreCase(existingAuthor.getName()))).collect(Collectors.toSet());
        addAuthorSet = updateAuthorSet.stream().filter(updateAuthor ->
                existingAuthorSet.stream().anyMatch(existingAuthor ->
                        !existingAuthor.getBirthday().equals(updateAuthor.getBirthday()) && !existingAuthor.getName().equalsIgnoreCase(updateAuthor.getName()))).collect(Collectors.toSet());

        long bookId = existingBook.getId();
        BeanUtils.copyProperties(bookDto, book);
        book.getAuthors().addAll(addAuthorSet);
        book.getAuthors().removeAll(deleteAuthorSet);
        book.setId(bookId);
        book = bookstoreRepository.save(book);
        return book;
    }

    @Override
    public List<BookDto> searchBook(BookInDto bookInDto) {
        List<BookDto> bookOutDtoList = new ArrayList<>();
//		bookOutDtoList = bookstoreRepository.findBookByTitleAuthors(bookInDto.getTitle(), bookInDto.getAuthors());

        if (StringUtils.isEmpty(bookInDto.getTitle()) && (bookInDto.getAuthors() == null || bookInDto.getAuthors().isEmpty())) {
            return bookOutDtoList;
//            throw new RestApiException("Book not found!", HttpStatus.BAD_REQUEST);
        }

        if (bookInDto.getTitle() == null && bookInDto.getAuthors().size() == 1) {
            bookOutDtoList = bookstoreRepository.findBookByAuthor(bookInDto.getAuthors().get(0));
        } else if (bookInDto.getTitle() == null && bookInDto.getAuthors().size() > 1) {
            bookOutDtoList = bookstoreRepository.findBookByAuthors(bookInDto.getAuthors());
        } else if (bookInDto.getAuthors() == null || bookInDto.getAuthors().isEmpty()) {
            bookOutDtoList = bookstoreRepository.findBookByTitle(bookInDto.getTitle());
        } else if (bookInDto.getAuthors().size() == 1) {
            bookOutDtoList = bookstoreRepository.findBookByTitleAuthor(bookInDto.getTitle(), bookInDto.getAuthors().get(0));
        } else {
            bookOutDtoList = bookstoreRepository.findBookByTitleAuthors(bookInDto.getTitle(), bookInDto.getAuthors());
        }

        if (bookOutDtoList.isEmpty()) {
            return bookOutDtoList;
//            throw new RestApiException("Book not found!", HttpStatus.BAD_REQUEST);
        } else {
            bookOutDtoList= bookOutDtoList.stream().map(bookDto -> {
                bookDto.setAuthors(authorRepository.findAuthorsByBookId(bookDto.getId()));
                return bookDto;
            }).collect(Collectors.toList());
        }

        return bookOutDtoList;
    }

    @Override
    public void deleteBook(Book book) {
        bookstoreRepository.delete(book);
    }
}
