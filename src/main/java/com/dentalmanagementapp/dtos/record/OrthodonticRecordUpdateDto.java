package com.dentalmanagementapp.dtos.record;

import jakarta.validation.constraints.NotBlank;

public record OrthodonticRecordUpdateDto(
        @NotBlank String description
) {
}
