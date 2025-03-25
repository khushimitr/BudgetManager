package org.example.budgetmanager.utils;

import org.example.budgetmanager.config.security.service.UserDetailsImpl;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Utils {
    public static String getCurrentUsernameFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;
    }

    public static Integer getCurrentUserIdFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl) {
                return ((UserDetailsImpl) principal).getId();
            } else {
                return null;
            }
        }
        return null;
    }

    public static BigDecimal processAmount(BigDecimal incomingAmount) {
        return incomingAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public static ResponseCookie convertToResponseCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .httpOnly(true)
                .secure(true) // recommended for HTTPS
                .path("/")
                .maxAge(86400) // 1 day
                .sameSite("Lax") // "Lax/Strict"
                .build();
    }

    public static int CURRENT_MONTH = LocalDate.now().getMonthValue();
    public static int CURRENT_YEAR = LocalDate.now().getYear();
}
