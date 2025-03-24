package com.dentalmanagementapp.dtos;

import com.dentalmanagementapp.entities.common.DiagnoseLegend;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record PolyvalentRecordUpdateDto(
        @NotBlank String description,
        @Nullable DiagnoseLegend legend
) {

}
