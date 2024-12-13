package com.example.BookStoreAPI.repository;

import com.example.BookStoreAPI.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    //@Query("SELECT u FROM AppUser u WHERE u.email = :email")
    Optional<AppUser> findByEmail(String email);
}
