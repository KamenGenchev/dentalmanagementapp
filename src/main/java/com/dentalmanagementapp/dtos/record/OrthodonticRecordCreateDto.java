package com.dentalmanagementapp.dtos.record;

import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public record OrthodonticRecordCreateDto(
        @Valid PatientForDentistDto patientDto,
        @NotNull String description
) {
}
