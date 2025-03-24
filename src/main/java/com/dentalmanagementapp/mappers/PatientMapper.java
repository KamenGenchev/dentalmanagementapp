package com.dentalmanagementapp.mappers;

import com.dentalmanagementapp.dtos.dentistpatient.DentistForPatientDto;
import com.dentalmanagementapp.dtos.patient.PatientAddDto;
import com.dentalmanagementapp.dtos.patient.PatientDetailedDto;
import com.dentalmanagementapp.dtos.patient.PatientDto;
import com.dentalmanagementapp.entities.Patient;
import com.dentalmanagementapp.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Component
@Validated
public class PatientMapper {
    private final DentistPatientMapper dentistPatientMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    @Autowired
    public PatientMapper(DentistPatientMapper dentistPatientMapper, PasswordEncoder passwordEncoder, SecurityUtil securityUtil) {
        this.dentistPatientMapper = dentistPatientMapper;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
    }

    public PatientDto toDto(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }

        return new PatientDto(
                patient.getEmail(),
                patient.getFirstName(),
                patient.getLastName()
        );
    }

    public PatientDetailedDto toDetailedDto(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }

        List<DentistForPatientDto> dentistDtos = patient.getDentistList()
                .stream()
                .map(dentistPatientMapper::toDentistOfPatientDto)
                .toList();

        return new PatientDetailedDto(
                toDto(patient),
                patient.getAddress(),
                patient.getPhoneNumber(),
                patient.getAge(),
                dentistDtos

        );
    }

    public Patient toPatient(@Valid PatientAddDto dto) {
        String encodedPassword = passwordEncoder.encode(securityUtil.generateAutomaticPassword());

        return new Patient.Builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(encodedPassword)
                .dateOfBirth(dto.dateOfBirth())
                .address(dto.address())
                .phoneNumber(dto.phoneNumber())
                .build();
    }
}
