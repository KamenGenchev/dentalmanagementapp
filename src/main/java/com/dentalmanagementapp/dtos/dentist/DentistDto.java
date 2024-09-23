package com.dentalmanagementapp.dtos.dentist;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DentistDto(
        @Email String email,
        @NotEmpty @Size(max = 50, message = "Max characters = 50") String firstName,
        @NotEmpty @Size(max = 50, message = "Max characters = 50") String lastName
){
}
