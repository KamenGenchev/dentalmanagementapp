package com.dentalmanagementapp.service.implementation;

import com.dentalmanagementapp.dtos.dentist.DentistRegisterDto;
import com.dentalmanagementapp.dtos.auth.LoginDto;
import com.dentalmanagementapp.dtos.auth.LoginResponseDto;
import com.dentalmanagementapp.security.JwtUtil;
import com.dentalmanagementapp.service.AuthService;
import com.dentalmanagementapp.service.DentistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final DentistService dentistService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, DentistService dentistService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.dentistService = dentistService;
    }

    @Override
    public void registerDentist(@Valid DentistRegisterDto dto) {
        dentistService.registerDentist(dto);
    }

    @Override
    public LoginResponseDto authenticateUser(@Valid LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(dto.email());
        return new LoginResponseDto(jwt, dto.email());
    }
}
