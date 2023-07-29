package com.bookstore.app.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.aspectj.lang.annotation.RequiredTypes;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto implements Serializable {

    public BookDto(Long id, String isbn, String title, int year, double price, String genre) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.price = price;
        this.genre = genre;
    }

    private Long id;

    @NotNull
    private String isbn;

    @NotNull
    private String title;

    @NotNull
    private List<AuthorDto> authors;

    @NotNull
    @Digits(integer = 4, fraction = 0)
    private int year;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    private double price;

    @NotNull
    private String genre;
}
