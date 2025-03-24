package com.dentalmanagementapp.dtos.record;

import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record OrthodonticRecordDto(
       @Valid PatientForDentistDto patientDto,
       @NotNull String description,
       @Nullable @PastOrPresent LocalDate date
) {
}
