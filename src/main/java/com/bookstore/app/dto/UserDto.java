package com.bookstore.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserDto implements Serializable {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private int isAdmin;

    @NotNull
    private Boolean isActive;
}
