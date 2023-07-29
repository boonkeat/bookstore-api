package com.bookstore.app.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(name="author_book",
            joinColumns=@JoinColumn(name="book_id"),
            inverseJoinColumns=@JoinColumn(name="author_id"))
    private Set<Author> authors = new HashSet<>();

    @Column(name = "year")
    private int year;

    @Column(name = "price")
    private double price;

    @Column(name = "genre")
    private String genre;
}
