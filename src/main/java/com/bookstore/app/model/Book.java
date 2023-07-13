package com.bookstore.app.model;

import java.io.Serializable;
import java.util.List;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="book")
public class Book implements Serializable {
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
