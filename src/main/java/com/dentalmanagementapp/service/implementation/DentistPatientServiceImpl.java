package com.dentalmanagementapp.service.implementation;

import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.entities.Dentist;
import com.dentalmanagementapp.entities.DentistPatient;
import com.dentalmanagementapp.entities.Patient;
import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.mappers.DentistMapper;
import com.dentalmanagementapp.mappers.DentistPatientMapper;
import com.dentalmanagementapp.mappers.PatientMapper;
import com.dentalmanagementapp.repository.DentistPatientRepository;
import com.dentalmanagementapp.repository.DentistRepository;
import com.dentalmanagementapp.service.DentistPatientService;
import com.dentalmanagementapp.validation.DentistValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class DentistPatientServiceImpl implements DentistPatientService {
    private final DentistValidation dentistValidator;
    private final DentistRepository dentistRepository;
    private final PatientMapper patientMapper;
    private final DentistPatientRepository dentistPatientRepository;
    private final DentistPatientMapper dentistPatientMapper;


    @Autowired
    public DentistPatientServiceImpl(DentistValidation dentistValidator, DentistRepository dentistRepository, PatientMapper patientMapper, DentistPatientRepository dentistPatientRepository, DentistPatientMapper dentistPatientMapper) {
        this.dentistValidator = dentistValidator;
        this.dentistRepository = dentistRepository;
        this.patientMapper = patientMapper;
        this.dentistPatientRepository = dentistPatientRepository;
        this.dentistPatientMapper = dentistPatientMapper;
    }


    @Transactional
    public void createPatientWithDentist(@Valid PatientAddDto dto, Long dentistId) {
        dentistValidator.requireNonNull(dentistId, "Dentist ID cannot be null");

        Dentist dentist = dentistRepository.findById(dentistId).
                orElseThrow(() -> new NotFoundException("Dentist with id: " + dentistId + " was not found"));

        if (dentistPatientRepository.patientExistsForDentist(dto.email())) { //err
            throw new IllegalArgumentException("Patient already exists for this dentist");
        }

        Patient patient = patientMapper.toPatient(dto);
        short localId = dentistPatientRepository.findNextLocalIdForDentist();

        DentistPatient dentistPatient = new DentistPatient(dentist, patient, localId);
        dentist.addPatient(dentistPatient);
        patient.addDentist(dentistPatient);

        dentistPatientRepository.save(dentistPatient);
    }

    public Page<DentistPatient> searchPatients(String firstName, String lastName, Pageable pageable) {//todo
        return dentistPatientRepository.searchPatientsByName(firstName, lastName, pageable);
    }

    @Override
    public DentistPatient getDentistPatientByPatientLocalId(short localId) {
        return dentistPatientRepository.findPatientByLocalId(localId)
                .orElseThrow(() -> new NotFoundException("Patient with local id: " + localId + " was not found for this dentist"));
    }

    @Override
    public Page<PatientForDentistDto> getAllPatientsForDentist(Long dentistId, Pageable pageable) {
        Dentist dentist = dentistRepository.findById(dentistId).
                orElseThrow(() -> new NotFoundException("Dentist with id: " + dentistId + " was not found"));

        return paginateList(Optional.ofNullable(dentist.getPatientList())
                .orElse(Collections.emptySet())
                .stream()
                .map(dentistPatientMapper::toPatientOfDentistDto)
                .collect(Collectors.toList()), pageable);
    }

    private <T> Page<T> paginateList(List<T> list, Pageable pageable) {
        int start = Math.min((int) pageable.getOffset(), list.size());
        int end = Math.min(start + pageable.getPageSize(), list.size());
        List<T> subList = list.subList(start, end);

        return new PageImpl<>(subList, pageable, list.size());
    }
}
