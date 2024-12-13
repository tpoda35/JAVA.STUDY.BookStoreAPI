package com.example.BookStoreAPI.repository;

import com.example.BookStoreAPI.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
}
