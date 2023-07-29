package com.bookstore.app.dto;

import com.bookstore.app.model.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto implements Serializable {
	private String name;
	private Date birthday;
}
