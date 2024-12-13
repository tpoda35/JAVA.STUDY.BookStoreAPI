package com.example.BookStoreAPI.controller;

import com.example.BookStoreAPI.dto.AppUserDto;
import com.example.BookStoreAPI.model.AppUser;
import com.example.BookStoreAPI.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

    @Autowired
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Operation(summary = "Gives back all the existing user.")
    @ApiResponse(responseCode = "404", description = "There's no existing user.")
    @ApiResponse(responseCode = "200", description = "User(s) successfully found.")
    @GetMapping("/allUser")
    public CompletableFuture<ResponseEntity<List<AppUser>>> getAllUser(){
        return appUserService.getAllUser()
                .thenApply(appUsers -> appUsers.isEmpty() ?
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(appUsers));
    }

    @Operation(summary = "Gives back a specific user.")
    @ApiResponse(responseCode = "404", description = "User not found.")
    @ApiResponse(responseCode = "200", description = "User found.")
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<AppUser>> getUser(@PathVariable Long id){
        return appUserService.getUser(id)
                .thenApply(appUser -> appUser == null ?
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(appUser));
    }

    @Operation(summary = "Adds a user to the database.")
    @ApiResponse(responseCode = "201", description = "User successfully added.")
    @PostMapping("/addUser")
    public CompletableFuture<ResponseEntity<AppUser>> addUser(@RequestBody @Valid AppUserDto appUserDto){
        return appUserService.addUser(appUserDto)
                .thenApply(appUser -> ResponseEntity.status(HttpStatus.CREATED).body(appUser));
    }

    @Operation(summary = "Deletes a user.")
    @ApiResponse(responseCode = "204", description = "User successfully removed.")
    @DeleteMapping("/deleteUser/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteUser(@PathVariable Long id){
        return appUserService.deleteUser(id)
                .thenApply(aVoid -> ResponseEntity.noContent().build());
    }
}