package com.bookstore.app.model;

import java.sql.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Entity
@Table(name="author")
@Getter
public class Author {
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "birthday")
	private Date birthday;

	@JsonIgnore
	@ManyToOne
	private Book book;
}
