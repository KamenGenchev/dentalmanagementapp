package com.dentalmanagementapp.security;

public record CurrentUser(
        Long currentUserId,
        boolean isAdmin,
        boolean isDentist,
        boolean isPatient
) {
}

