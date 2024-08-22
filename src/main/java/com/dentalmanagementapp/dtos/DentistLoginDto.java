package com.dentalmanagementapp.dtos;

import jakarta.validation.constraints.NotNull;

public record DentistLoginDto(
        @NotNull String email,
        @NotNull String password
) {
}
