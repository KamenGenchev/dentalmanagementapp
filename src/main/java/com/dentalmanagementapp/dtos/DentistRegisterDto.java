package com.dentalmanagementapp.dtos;

import jakarta.validation.constraints.NotNull;

public record DentistRegisterDto(
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String email,
        @NotNull String password
) {
}
