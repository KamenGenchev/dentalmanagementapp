package com.dentalmanagementapp.dtos.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record PatientDto(
        @Email String email,
        @NotEmpty String firstName,
        @NotEmpty String lastName
) {
}
