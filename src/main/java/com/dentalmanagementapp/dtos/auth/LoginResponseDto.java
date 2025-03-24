package com.dentalmanagementapp.dtos.auth;

public record LoginResponseDto(
        String jwt,
        String email
) {
}
