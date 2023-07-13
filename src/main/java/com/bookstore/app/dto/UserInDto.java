package com.bookstore.app.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInDto {
	@NotNull
    private String username;

    @NotNull
    private String password;
}
