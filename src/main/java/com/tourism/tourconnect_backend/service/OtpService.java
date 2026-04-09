package com.tourism.tourconnect_backend.service;

import com.tourism.tourconnect_backend.model.Otp;
import com.tourism.tourconnect_backend.model.User;
import com.tourism.tourconnect_backend.repository.OtpRepository;
import com.tourism.tourconnect_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // ✅ GENERATE OTP
    public String generateOtp(String email) {

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        Otp otpEntity = otpRepository.findByEmail(email).orElse(new Otp());

        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setVerified(false);
        otpEntity.setExpiryTime(System.currentTimeMillis() + 5 * 60 * 1000);

        otpRepository.save(otpEntity);

        emailService.sendOtp(email, otp);

        System.out.println("OTP: " + otp);

        return otp;   // 🔥 IMPORTANT (return actual OTP)
    }

    // ✅ VERIFY OTP
    public boolean verifyOtp(String email, String otp) {

        Otp stored = otpRepository.findByEmail(email).orElse(null);

        if (stored == null) return false;

        if (stored.getExpiryTime() < System.currentTimeMillis()) return false;

        boolean isValid = stored.getOtp().equals(otp);

        if (isValid) {
            stored.setVerified(true);
            otpRepository.save(stored);
        }

        return isValid;
    }

    // ✅ RESET PASSWORD
    public void resetPassword(String email, String newPassword) {

        Otp stored = otpRepository.findByEmail(email).orElse(null);

        if (stored == null || !stored.isVerified()) {
            throw new RuntimeException("OTP not verified");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(newPassword); // 🔐 (later you can encrypt)

        userRepository.save(user);

        // Reset OTP
        stored.setVerified(false);
        otpRepository.save(stored);
    }
}