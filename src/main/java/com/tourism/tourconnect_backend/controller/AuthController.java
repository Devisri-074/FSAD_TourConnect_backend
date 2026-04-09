package com.tourism.tourconnect_backend.controller;

import com.tourism.tourconnect_backend.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private OtpService otpService;

    // ✅ SEND OTP
    @GetMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        otpService.generateOtp(email);  // already sends email
        return "OTP sent successfully";
    }

    // ✅ VERIFY OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp) {

        boolean isValid = otpService.verifyOtp(email, otp);

        return isValid ? "OTP Verified" : "Invalid OTP";
    }

    // ✅ RESET PASSWORD
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword) {

        otpService.resetPassword(email, newPassword);

        return "Password reset successful";
    }
}