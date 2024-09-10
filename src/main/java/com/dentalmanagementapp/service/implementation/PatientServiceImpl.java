package com.dentalmanagementapp.service.implementation;

import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.dtos.patient.PatientDetailedDto;
import com.dentalmanagementapp.dtos.patient.PatientDto;
import com.dentalmanagementapp.dtos.patient.PatientUpdateDto;
import com.dentalmanagementapp.entities.Patient;
import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.mappers.PatientMapper;
import com.dentalmanagementapp.repository.PatientRepository;
import com.dentalmanagementapp.service.PatientService;
import com.dentalmanagementapp.validation.PatientValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final DentistPatientServiceImpl dentistPatientService;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PatientValidation patientValidator, PatientMapper patientMapper, DentistPatientServiceImpl dentistPatientService) {
        this.patientRepository = patientRepository;
        this.patientValidator = patientValidator;
        this.patientMapper = patientMapper;
        this.dentistPatientService = dentistPatientService;
    }

    @Override
    @Transactional
    public Long createPatient(@Valid PatientAddDto dto) {
        Patient patient = createAndSavePatient(dto);
        return patient.getId();
    }

    @Override
    @Transactional
    public Long createPatientWithDentist(@Valid PatientAddDto dto, Long dentistId) {
        patientValidator.requireNonNull(dentistId, "Dentist ID cannot be null");
        Patient patient = createAndSavePatient(dto);
        dentistPatientService.savePatientToDentist(patient, dentistId);

        return patient.getId();
    }

    private Patient createAndSavePatient(@Valid PatientAddDto dto) {
        patientValidator.assertDoesNotExistByEmail(dto.email());
        Patient patient = patientMapper.toPatient(dto);
        patientRepository.save(patient);
        return patient;
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
            patientValidator.assertDoesNotExistByEmail(dto.email());
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
        patientValidator.assertExistsById(id);

        patientRepository.deleteById(id);
    }

}
