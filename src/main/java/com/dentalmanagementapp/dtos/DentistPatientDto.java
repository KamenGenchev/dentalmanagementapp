package com.dentalmanagementapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DentistPatientDto(
        @NotBlank
        @Size(min = 1, max = 50)
        String patientFirstName,

        @NotBlank
        @Size(min = 1, max = 50)
        String patientLastName,

        short localId
) {

}
