package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.PatientRecordsDto;
import com.dentalmanagementapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    public ResponseEntity<PatientRecordsDto> getRecords() {
        return patientService.getAllRecordsForPatient();
    }

}
