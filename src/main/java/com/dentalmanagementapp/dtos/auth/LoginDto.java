package com.dentalmanagementapp.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @NotEmpty @Email String email,
        @NotEmpty @Size(min = 8, max = 30, message = "Password must be between 8 and 25 characters!") String password
) {
}
