package com.dentalmanagementapp.controllers;


import com.dentalmanagementapp.dtos.LoginDto;
import com.dentalmanagementapp.dtos.DentistRegisterDto;
import com.dentalmanagementapp.dtos.LoginResponseDto;
import com.dentalmanagementapp.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        authService.registerDentist(dto); //todo validate whether dentist is not already registered
        LoginDto loginDto = new LoginDto(dto.email(), dto.password());
        return login(loginDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto dto) {
        try {
            LoginResponseDto jwtResponse = authService.authenticateUser(dto);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtResponse.jwt());
            return ResponseEntity.ok().headers(headers).body(jwtResponse);

        } catch (AuthenticationException exception) {
            return ResponseEntity.badRequest().body("Login failed");
        }
    }

    @PostMapping("/login/patients")
    public ResponseEntity<Object> patientLogin(@Email @RequestBody String email) {
        if (authService.registerPatient(email)) {
            return ResponseEntity.ok("Check email for password.");
        }
        return ResponseEntity.badRequest().body("Patient registration failed");
    }


}

