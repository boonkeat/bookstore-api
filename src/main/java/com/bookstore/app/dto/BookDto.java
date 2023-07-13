package com.bookstore.app.dto;

import com.bookstore.app.model.Author;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class BookDto implements Serializable {

    private Long id;

    @NotNull
    private String isbn;

    @NotNull
    private String title;

    @NotNull
    private List<Author> authors;

    @NotNull
    @Digits(integer = 4, fraction = 0)
    private int year;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    private double price;

    @NotNull
    private String genre;
}
