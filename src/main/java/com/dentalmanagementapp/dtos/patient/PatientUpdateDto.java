package com.dentalmanagementapp.dtos.patient;

import jakarta.annotation.Nullable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientUpdateDto(
        @Email String email,
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @Nullable String address,
        @Nullable String phoneNumber,
        @NotNull LocalDate dateOfBirth
) {
}
