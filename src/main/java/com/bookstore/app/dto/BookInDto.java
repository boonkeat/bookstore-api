package com.bookstore.app.dto;

import com.bookstore.app.model.Author;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookInDto implements Serializable {
    private String title;
    private List<String> authors;
}
