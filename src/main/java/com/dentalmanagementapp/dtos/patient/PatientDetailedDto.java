package com.dentalmanagementapp.dtos.patient;

import com.dentalmanagementapp.dtos.dentistpatient.DentistForPatientDto;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PatientDetailedDto(
        @Valid PatientDto patient,
        @Nullable String address,
        @Nullable String phoneNumber,
        @NotNull int age,
        List<@Valid DentistForPatientDto> dentists
) {
}
