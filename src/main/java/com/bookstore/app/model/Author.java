package com.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.Comparator;

@Entity
@Data
@Table(name="author")
public class Author implements Serializable {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "birthday")
	private Date birthday;

	public int compareTo(Author o) {
		return Comparator.comparing(Author::getName)
				.thenComparing(Author::getBirthday)
				.compare(this, o);
	}
}
