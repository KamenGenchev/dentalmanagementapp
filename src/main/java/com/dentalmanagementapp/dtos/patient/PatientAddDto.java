package com.dentalmanagementapp.dtos.patient;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatientAddDto(
        @NotEmpty @Size(min = 1, max = 50) String firstName,
        @NotEmpty @Size(min = 1, max = 50) String lastName,
        @NotEmpty @Email String email,
        @NotNull LocalDate dateOfBirth,
        @Nullable String address,
        @Nullable String phoneNumber
) {
}
