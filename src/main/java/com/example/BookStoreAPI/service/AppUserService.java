package com.example.BookStoreAPI.service;

import com.example.BookStoreAPI.dto.AppUserDto;
import com.example.BookStoreAPI.exception.AlreadyExistsException;
import com.example.BookStoreAPI.exception.DeleteException;
import com.example.BookStoreAPI.model.AppUser;
import com.example.BookStoreAPI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class AppUserService {

    @Autowired
    private final UserRepository userRepository;

    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Async
    public CompletableFuture<List<AppUser>> getAllUser() {
        return CompletableFuture.supplyAsync(userRepository::findAll);
    }

    @Async
    public CompletableFuture<AppUser> getUser(Long id){
        return CompletableFuture.supplyAsync(() -> userRepository.findById(id).orElse(null));
    }

    @Async
    public CompletableFuture<AppUser> addUser(@Valid AppUserDto appUserDto){
        Optional<AppUser> userExists = userRepository.findByEmail(appUserDto.getEmail());
        if (userExists.isPresent()){
            throw new AlreadyExistsException("User already exists with this email: " + appUserDto.getEmail());
        }

        AppUser appUser = new AppUser();

        appUser.setUsername(appUserDto.getUsername());
        appUser.setEmail(appUserDto.getEmail());

        return CompletableFuture.completedFuture(userRepository.save(appUser));
    }

    @Async
    public CompletableFuture<Void> deleteUser(Long id){
        if (userRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("User with id " + id + " not found");
        }

        try {
            userRepository.deleteById(id); //Modify to delete the books also

            return CompletableFuture.completedFuture(null);
        } catch (Exception ex){
            throw new DeleteException("Delete failed with the next id: " + id);
        }
    }
}
