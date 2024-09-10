package com.dentalmanagementapp.controllers;


import com.dentalmanagementapp.dtos.auth.LoginDto;
import com.dentalmanagementapp.dtos.dentist.DentistRegisterDto;
import com.dentalmanagementapp.dtos.auth.LoginResponseDto;
import com.dentalmanagementapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService service) {
        this.authService = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerDentistAndLogin(@Valid @RequestBody DentistRegisterDto dto) {
        authService.registerDentist(dto);
        LoginDto loginDto = new LoginDto(dto.email(), dto.password());
        return login(loginDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto dto) {
        LoginResponseDto jwtResponse = authService.authenticateUser(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtResponse.jwt());

        return ResponseEntity.ok().headers(headers).body(jwtResponse);
    }

}

