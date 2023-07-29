package com.bookstore.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    private String accessToken ;
    private String tokenType ="Bearer ";

    public LoginDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
