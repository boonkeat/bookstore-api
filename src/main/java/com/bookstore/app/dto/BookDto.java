package com.bookstore.app.dto;

import com.bookstore.app.model.Author;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
