package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.DentistRegisterDto;
import com.dentalmanagementapp.entities.Dentist;
import jakarta.validation.Valid;

public interface AuthService {
    Dentist registerDentist(@Valid DentistRegisterDto dto);
}
