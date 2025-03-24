package com.dentalmanagementapp.dtos.dentistpatient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DentistForPatientDto(
        @Email String email,

        @NotBlank
        @Size(min = 1, max = 50)
        String dentistFirstName,

        @NotBlank
        @Size(min = 1, max = 50)
        String dentistLastName
) {
}
