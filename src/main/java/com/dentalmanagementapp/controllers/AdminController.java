package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.dtos.patient.PatientDetailedDto;
import com.dentalmanagementapp.dtos.dentist.DentistDetailsDto;
import com.dentalmanagementapp.dtos.dentist.DentistDto;
import com.dentalmanagementapp.dtos.patient.PatientDto;
import com.dentalmanagementapp.dtos.patient.PatientUpdateDto;
import com.dentalmanagementapp.service.DentistService;
import com.dentalmanagementapp.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final DentistService dentistService;
    private final PatientService patientService;

    @Autowired
    public AdminController(DentistService dentistService, PatientService patientService) {
        this.dentistService = dentistService;
        this.patientService = patientService;
    }

    @GetMapping("/dentists")
    public ResponseEntity<List<DentistDto>> getAllDentists() {
        List<DentistDto> dentists = dentistService.getAllDentists();
        return new ResponseEntity<>(dentists, HttpStatus.OK);
    }

    @GetMapping("/dentists/{id}")
    public ResponseEntity<DentistDetailsDto> getDentistById(@PathVariable Long id) {
        DentistDetailsDto dentist = dentistService.getDentistById(id);
        return new ResponseEntity<>(dentist, HttpStatus.OK);
    }

    @PostMapping("/dentists")
    public ResponseEntity<Long> createDentist(@Valid @RequestBody DentistDto dentistDto) {
        Long newDentistId = dentistService.createDentist(dentistDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(newDentistId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/dentists/{id}")
    public ResponseEntity<Void> updateDentist(@PathVariable Long id, @Valid @RequestBody DentistDto dentistDto) {
        dentistService.updateDentist(id, dentistDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/dentists/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable Long id) {
        dentistService.deleteDentist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDetailedDto> getPatientById(@PathVariable Long id) {
        PatientDetailedDto patient = patientService.getPatientById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping("/patients")
    public ResponseEntity<Long> createPatient(@Valid @RequestBody PatientAddDto patientDto) {
        Long newPatientID = patientService.createPatient(patientDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(newPatientID)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<Void> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientUpdateDto patientUpdateDto) {
        patientService.updatePatient(id, patientUpdateDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
