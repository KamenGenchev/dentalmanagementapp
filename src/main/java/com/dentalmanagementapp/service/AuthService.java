package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.auth.DentistRegisterDto;
import com.dentalmanagementapp.dtos.auth.LoginDto;
import com.dentalmanagementapp.dtos.auth.LoginResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    void registerDentist(@Valid DentistRegisterDto dto);
    LoginResponseDto authenticateUser(@Valid LoginDto dto);
}
