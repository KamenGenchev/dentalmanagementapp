package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.PatientRecordsDto;
import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.dtos.patient.PatientDetailedDto;
import com.dentalmanagementapp.dtos.patient.PatientDto;
import com.dentalmanagementapp.dtos.patient.PatientUpdateDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientService {
    Long createPatient(PatientAddDto dto);
    PatientDetailedDto getPatientById(Long id);
    List<PatientDto> getAllPatients();
    void updatePatient(Long id, PatientUpdateDto patientUpdateDtoDto);
    void deletePatient(Long id);
    ResponseEntity<PatientRecordsDto> getAllRecordsForPatient();
}
