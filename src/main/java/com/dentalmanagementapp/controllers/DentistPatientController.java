package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.service.DentistPatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

import static com.dentalmanagementapp.security.UserContext.getCurrentUser;

@RestController
@RequestMapping("/api/dentists/patients")
public class DentistPatientController {
    private final DentistPatientService dentistPatientService;

    @Autowired
    public DentistPatientController(DentistPatientService dentistPatientService) {
        this.dentistPatientService = dentistPatientService;
    }

    @PostMapping("/new")
    public ResponseEntity<Void> addNewPatientToDentist(@Valid @RequestBody PatientAddDto patientDto) {
        dentistPatientService.createPatientWithDentist(patientDto, getCurrentUser().currentUserId()); //errr
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Page<PatientForDentistDto>> getPatients(@RequestBody Map<String, Integer> paginationRequest) {
        int page = paginationRequest.get("page");
        int size = paginationRequest.get("size");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "localId"));
        Page<PatientForDentistDto> patientPage = dentistPatientService.getAllPatientsForDentist(getCurrentUser().currentUserId(), pageable);

        return ResponseEntity.ok(patientPage);
    }
}
