package com.example.documentsstoragingsepolia.service;


import com.example.documentsstoragingsepolia.model.Role;
import com.example.documentsstoragingsepolia.model.User;
import com.example.documentsstoragingsepolia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user){
        return repository.save(user);
    }

    public User create(User user){
        if (repository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        if (repository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        return save(user);
    }

    public User getByUsername(String username){
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));
    }

    public UserDetailsService userDetailsService(){
        return this::getByUsername;
    }

    public User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}
