package com.bookstore.app.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
@Table(name="author")
public class Author implements Serializable {
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
