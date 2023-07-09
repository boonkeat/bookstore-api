package com.bookstore.app.dto;

import javax.validation.constraints.NotNull;

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
