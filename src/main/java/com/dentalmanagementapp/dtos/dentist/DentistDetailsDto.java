package com.dentalmanagementapp.dtos.dentist;

import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DentistDetailsDto(
        @NotNull @Valid DentistDto dentist,
        @NotNull List<@Valid PatientForDentistDto> patientList
) {
}
