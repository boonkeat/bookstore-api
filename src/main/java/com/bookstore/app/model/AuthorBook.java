package com.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name="author_book")
public class AuthorBook implements Serializable {

    @Id
    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "book_id")
    private Long bookId;
}
