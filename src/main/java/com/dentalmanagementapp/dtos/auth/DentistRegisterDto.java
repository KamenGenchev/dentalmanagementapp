package com.dentalmanagementapp.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DentistRegisterDto(
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotEmpty @Email String email,
        @NotEmpty @Size(min = 8) String password
) {
}
