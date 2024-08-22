package com.dentalmanagementapp.dtos;


import jakarta.validation.constraints.NotNull;

public record PatientDto(
        @NotNull Long localId,
        @NotNull String firstName,
        @NotNull String lastName
) {
}
