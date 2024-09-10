package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.dentist.DentistDetailsDto;
import com.dentalmanagementapp.dtos.dentist.DentistDto;
import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.service.DentistPatientService;
import com.dentalmanagementapp.service.DentistService;
import com.dentalmanagementapp.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/dentists")
public class DentistController {
    private final DentistService dentistService;
    private final DentistPatientService dentistPatientService;
    private final SecurityUtil securityUtil;

    @Autowired
    public DentistController(DentistService dentistService, DentistPatientService dentistPatientService, SecurityUtil securityUtil) {
        this.dentistService = dentistService;
        this.dentistPatientService = dentistPatientService;
        this.securityUtil = securityUtil;
    }

    @GetMapping()
    public ResponseEntity<DentistDetailsDto> getProfile() {
        DentistDetailsDto dentist = dentistService.getDentistById(securityUtil.getCurrentUserId());
        return new ResponseEntity<>(dentist, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Void> updateSelf(@Valid @RequestBody DentistDto dentistDto) {
        dentistService.updateDentist(securityUtil.getCurrentUserId(), dentistDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-patient")
    public ResponseEntity<Void> addNewPatientToDentist(@Valid @RequestBody PatientAddDto patientDto) {
        dentistPatientService.createPatientWithDentist(patientDto, securityUtil.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }
}