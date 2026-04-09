package com.tourism.tourconnect_backend.service;

import com.tourism.tourconnect_backend.model.Otp;
import com.tourism.tourconnect_backend.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    public String generateOtp(String email) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        Otp otpEntity = otpRepository.findByEmail(email).orElse(new Otp());
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(System.currentTimeMillis() + 5 * 60 * 1000);

        otpRepository.save(otpEntity);

        emailService.sendOtp(email, otp);

        return "OTP sent successfully";
    }

    public boolean verifyOtp(String email, String otp) {
        Otp stored = otpRepository.findByEmail(email).orElse(null);

        if (stored == null) return false;
        if (stored.getExpiryTime() < System.currentTimeMillis()) return false;

        return stored.getOtp().equals(otp);
    }
}