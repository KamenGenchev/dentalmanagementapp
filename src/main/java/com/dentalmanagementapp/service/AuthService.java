package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.dentist.DentistRegisterDto;
import com.dentalmanagementapp.dtos.auth.LoginDto;
import com.dentalmanagementapp.dtos.auth.LoginResponseDto;

public interface AuthService {
    void registerDentist(DentistRegisterDto dto);
    LoginResponseDto authenticateUser(LoginDto dto);
}
