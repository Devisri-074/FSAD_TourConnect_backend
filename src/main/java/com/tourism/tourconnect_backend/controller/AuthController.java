package com.tourism.tourconnect_backend.controller;

import com.tourism.tourconnect_backend.model.User;
import com.tourism.tourconnect_backend.service.OtpService;
import com.tourism.tourconnect_backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://fsad-project-tour-connect-frontend.vercel.app", allowCredentials = "true")
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserService userService;

    // ✅ SIGNUP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (user.getRole() == null) user.setRole("TOURIST");
        // frontend sends "name" field — ensure fullName is set
        if (user.getFullName() == null && user.getName() != null) {
            user.setFullName(user.getName());
        }
        boolean needsApproval = user.getRole().equalsIgnoreCase("GUIDE") || user.getRole().equalsIgnoreCase("HOST");
        user.setApprovalStatus(needsApproval ? "pending" : "approved");
        User saved = userService.save(user);
        return ResponseEntity.ok(saved);
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody java.util.Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return userService.login(email, password)
                .map(u -> ResponseEntity.ok((Object) u))
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }

    // ✅ SEND OTP
    @GetMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        otpService.generateOtp(email);
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

    // ✅ GOOGLE LOGIN SUCCESS
    @GetMapping("/google/success")
    public void handleGoogleSuccess(
            @AuthenticationPrincipal OAuth2User principal,
            HttpServletResponse response
    ) throws Exception {

        if (principal == null) {
            response.sendError(401, "OAuth failed");
            return;
        }

        String email = (String) principal.getAttributes().get("email");
        String name = (String) principal.getAttributes().get("name");

        User user = userService.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setRole("TOURIST");
            user.setApprovalStatus("approved");
            userService.save(user);
        }

        // ✅ Redirect to frontend
        response.sendRedirect("https://fsad-project-tour-connect-frontend.vercel.app/dashboard");
    }

    // ✅ NEW: GET CURRENT LOGGED-IN USER (VERY IMPORTANT)
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(
            @AuthenticationPrincipal OAuth2User principal
    ) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }

        String email = (String) principal.getAttributes().get("email");
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(user);
    }
}