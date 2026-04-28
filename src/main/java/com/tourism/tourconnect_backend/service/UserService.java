package com.tourism.tourconnect_backend.service;

import com.tourism.tourconnect_backend.model.User;
import com.tourism.tourconnect_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // ✅ SIGNUP
    public User signup(User user) {
        return repo.save(user);
    }

    // ✅ LOGIN
    public Optional<User> login(String email, String password) {
        Optional<User> user = repo.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }

        return Optional.empty();
    }

    // ✅ FIND BY EMAIL (🔥 REQUIRED FOR GOOGLE LOGIN)
    public User findByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    // ✅ SAVE USER (🔥 REQUIRED FOR GOOGLE LOGIN)
    public User save(User user) {
        return repo.save(user);
    }

    // ✅ RESET PASSWORD
    public String resetPassword(String email, String newPassword) {
        Optional<User> userOpt = repo.findByEmail(email);

        if (userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();
        user.setPassword(newPassword);
        repo.save(user);

        return "Password updated successfully";
    }
}