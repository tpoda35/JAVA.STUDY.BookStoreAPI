package com.example.BookStoreAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title field cannot be blank.")
    private String title;

    @NotNull
    private Integer pageNumber;

    @ManyToOne
    @JoinColumn(name = "appUserId", nullable = false)
    @JsonIgnore
    private AppUser appUser;
}
