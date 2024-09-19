package com.dentalmanagementapp.service.implementation;

import com.dentalmanagementapp.dtos.PatientRecordsDto;
import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.dtos.patient.PatientDetailedDto;
import com.dentalmanagementapp.dtos.patient.PatientDto;
import com.dentalmanagementapp.dtos.patient.PatientUpdateDto;
import com.dentalmanagementapp.dtos.record.OrthodonticRecordDto;
import com.dentalmanagementapp.dtos.record.PolyvalentRecordDto;
import com.dentalmanagementapp.entities.Patient;
import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.mappers.PatientMapper;
import com.dentalmanagementapp.repository.PatientRepository;
import com.dentalmanagementapp.service.OrthodonticRecordService;
import com.dentalmanagementapp.service.PatientService;
import com.dentalmanagementapp.service.PolyvalentRecordService;
import com.dentalmanagementapp.validation.PatientValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Validated
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientValidation patientValidator;
    private final PatientMapper patientMapper;
    private final OrthodonticRecordService orthodonticRecordService;
    private final PolyvalentRecordService polyvalentRecordService;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PatientValidation patientValidator, PatientMapper patientMapper, OrthodonticRecordService orthodonticRecordService, PolyvalentRecordService polyvalentRecordService) {
        this.patientRepository = patientRepository;
        this.patientValidator = patientValidator;
        this.patientMapper = patientMapper;
        this.orthodonticRecordService = orthodonticRecordService;
        this.polyvalentRecordService = polyvalentRecordService;
    }

    @Override
    @Transactional
    public Long createPatient(@Valid PatientAddDto dto) {
        patientValidator.validateEmailUniqueness(dto.email());

        Patient patient = patientMapper.toPatient(dto);
        patientRepository.save(patient);
        return patient.getId();
    }

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), List::copyOf));
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDetailedDto getPatientById(Long id) {
        patientValidator.requireNonNull(id, "Patient ID cannot be null");

        return patientRepository.findById(id)
                .map(patientMapper::toDetailedDto)
                .orElseThrow(() -> new NotFoundException("Patient with id: " + id + " was not found"));
    }

    @Override
    @Transactional
    public void updatePatient(Long id, @Valid PatientUpdateDto dto) {
        patientValidator.requireNonNull(id, "Patient ID cannot be null");

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient with id: " + id + " was not found"));

        if (!Objects.equals(patient.getEmail(), dto.email())) {
            patientValidator.validateEmailUniqueness(dto.email());
        }

        patient.updateInformation(
                dto.email(),
                dto.firstName(),
                dto.lastName(),
                dto.address(),
                dto.phoneNumber(),
                dto.dateOfBirth()
        );

        patientRepository.save(patient);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        patientValidator.requireNonNull(id, "Patient ID cannot be null");
        patientValidator.validatePatientExists(id);

        patientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PatientRecordsDto> getAllRecordsForPatient() {
        List<PolyvalentRecordDto> polyvalentRecords = polyvalentRecordService.getAllRecords();
        List<OrthodonticRecordDto> orthodonticRecords = orthodonticRecordService.getAllRecords();

        PatientRecordsDto patientRecords = new PatientRecordsDto(polyvalentRecords, orthodonticRecords);

        return ResponseEntity.ok(patientRecords);
    }

}
