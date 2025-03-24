package com.dentalmanagementapp.dtos.record;

import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import com.dentalmanagementapp.entities.common.DiagnoseLegend;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record PolyvalentRecordDto(
        @Valid PatientForDentistDto patientDto,
        @NotNull String description,
        @PastOrPresent LocalDate date,
        @NotNull DiagnoseLegend legend
) {
}