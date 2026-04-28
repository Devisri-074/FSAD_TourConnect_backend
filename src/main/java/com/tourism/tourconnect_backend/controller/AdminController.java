package com.tourism.tourconnect_backend.controller;

import com.tourism.tourconnect_backend.model.Booking;
import com.tourism.tourconnect_backend.model.Homestay;
import com.tourism.tourconnect_backend.model.User;
import com.tourism.tourconnect_backend.repository.BookingRepository;
import com.tourism.tourconnect_backend.repository.HomestayRepository;
import com.tourism.tourconnect_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://fsad-project-tour-connect-frontend.vercel.app", allowCredentials = "true")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HomestayRepository homestayRepository;

    // ✅ GET ALL USERS — matches frontend: fetch("http://localhost:8080/api/users")
    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ UPDATE USER STATUS — matches frontend: PUT /api/users/{id}/status
    @PutMapping("/api/users/{id}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return userRepository.findById(id).map(user -> {
            user.setApprovalStatus(body.get("status"));
            userRepository.save(user);
            return ResponseEntity.ok((Object) user);
        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE USER
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) return ResponseEntity.notFound().build();
        userRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    // ✅ Legacy admin-prefixed routes (keep for backward compat)
    @GetMapping("/api/admin/users")
    public List<User> getAllUsersAdmin() {
        return userRepository.findAll();
    }

    @GetMapping("/api/admin/bookings")
    public List<Booking> getAllBookingsAdmin() {
        return bookingRepository.findAll();
    }

    @GetMapping("/api/admin/properties")
    public List<Homestay> getAllPropertiesAdmin() {
        return homestayRepository.findAll();
    }

    @PutMapping("/api/admin/user/{id}")
    public ResponseEntity<?> updateUserStatusAdmin(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return userRepository.findById(id).map(user -> {
            user.setApprovalStatus(body.get("status"));
            userRepository.save(user);
            return ResponseEntity.ok((Object) user);
        }).orElse(ResponseEntity.notFound().build());
    }
}
