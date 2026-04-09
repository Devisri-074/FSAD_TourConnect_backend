package com.tourism.tourconnect_backend.controller;

import com.tourism.tourconnect_backend.model.User;
import com.tourism.tourconnect_backend.service.UserService;
import com.tourism.tourconnect_backend.service.OtpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private OtpService otpService; // ✅ ADD THIS

    // ✅ Signup
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return service.signup(user);
    }

    // ✅ Login
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return service.login(user.getEmail(), user.getPassword())
                .orElse(null);
    }

    // 🔥 1. SEND OTP
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        return otpService.generateOtp(email);
    }

    // 🔥 2. VERIFY OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp) {

        boolean valid = otpService.verifyOtp(email, otp);

        return valid ? "OTP verified" : "Invalid OTP";
    }

    // 🔥 3. RESET PASSWORD
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword) {
        return service.resetPassword(email, newPassword);
    }
}