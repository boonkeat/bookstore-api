package com.bookstore.app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
	@NotNull
    private String username;

    @NotNull
    private String password;
    
    @NotNull
    private int isAdmin;
}
