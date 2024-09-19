package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.dtos.patient.PatientDetailedDto;
import com.dentalmanagementapp.dtos.patient.PatientDto;
import com.dentalmanagementapp.dtos.patient.PatientUpdateDto;
import com.dentalmanagementapp.entities.common.PatientRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientService {
    Long createPatient(PatientAddDto dto);
    PatientDetailedDto getPatientById(Long id);
    List<PatientDto> getAllPatients();
    void updatePatient(Long id, PatientUpdateDto patientUpdateDtoDto);
    void deletePatient(Long id);
    ResponseEntity<List<PatientRecord>> getAllRecordsForPatient(Long currentUserId);
}
