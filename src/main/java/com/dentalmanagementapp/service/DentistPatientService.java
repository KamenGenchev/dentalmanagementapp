package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.entities.DentistPatient;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DentistPatientService {
    void createPatientWithDentist(@Valid PatientAddDto dto, Long dentistId);

    DentistPatient getDentistPatientByPatientLocalId(short localId);

    Page<PatientForDentistDto> getAllPatientsForDentist(Long dentistId, Pageable pageable);
}
