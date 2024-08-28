package com.dentalmanagementapp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record DentistRegisterDto(
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotEmpty @Email String email,
        @NotEmpty String password
) {
}
