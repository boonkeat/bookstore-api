package com.bookstore.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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
