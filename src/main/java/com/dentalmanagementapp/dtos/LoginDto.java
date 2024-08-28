package com.dentalmanagementapp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @NotEmpty @Email String email,
        @NotEmpty @Size(min = 8, max = 25) String password
) {
}
