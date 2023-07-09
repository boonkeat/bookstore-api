package com.bookstore.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="book")
@Getter
@Setter
public class Book {
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "isbn")
	private String isbn;
	
	@Column(name = "title")
	private String title;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "book")
	private List<Author> authors;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "genre")
	private String genre;
}
