package com.dentalmanagementapp.util;

import com.dentalmanagementapp.exception.custom.NoAuthorizedUserException;
import com.dentalmanagementapp.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SecurityUtil { //todo
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    private CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new NoAuthorizedUserException();
        }
        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails;
        }
        throw new NoAuthorizedUserException("Principal is not an instance of CustomUserDetails.");
    }

    public String generateAutomaticPassword() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    public boolean isDentist() {
        return hasRole("ROLE_DENTIST");
    }

    public boolean isPatient() {
        return hasRole("ROLE_PATIENT");
    }

    private boolean hasRole(String role) {
        CustomUserDetails user = getCurrentUser();
        return user.getAuthorities().contains(new SimpleGrantedAuthority(role));
    }
}
    



