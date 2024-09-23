package com.dentalmanagementapp.dtos.dentistpatient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PatientForDentistDto(
        @NotBlank
        @Size(min = 1, max = 50)
        String patientFirstName,

        @NotBlank
        @Size(min = 1, max = 50)
        String patientLastName,

        @NotNull
        short localId
) {

}
