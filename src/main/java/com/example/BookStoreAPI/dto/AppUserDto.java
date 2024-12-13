package com.example.BookStoreAPI.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AppUserDto {
    @NotBlank(message = "Username field cannot be blank.")
    @Length(min = 5, max = 20, message = "Username must be between 5 and 20 characters.")
    private String username;

    @NotBlank(message = "Email field cannot be blank.")
    @Email(message = "Email is invalid.")
    private String email;
}
