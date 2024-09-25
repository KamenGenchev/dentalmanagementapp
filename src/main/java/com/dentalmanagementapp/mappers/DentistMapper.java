package com.dentalmanagementapp.mappers;

import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import com.dentalmanagementapp.dtos.dentist.DentistDetailsDto;
import com.dentalmanagementapp.dtos.dentist.DentistDto;
import com.dentalmanagementapp.dtos.auth.DentistRegisterDto;
import com.dentalmanagementapp.entities.Dentist;
import com.dentalmanagementapp.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Validated
public class DentistMapper {
    private final DentistPatientMapper dentistPatientMapper;
    private final SecurityUtil securityUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DentistMapper(DentistPatientMapper dentistPatientMapper, SecurityUtil securityUtil, PasswordEncoder passwordEncoder) {
        this.dentistPatientMapper = dentistPatientMapper;
        this.securityUtil = securityUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public DentistDto toDto(Dentist dentist) {
        if (dentist == null) {
            throw new IllegalArgumentException("Dentist cannot be null");
        }

        return new DentistDto(
                dentist.getEmail(),
                dentist.getFirstName(),
                dentist.getLastName()
        );
    }

    public Dentist fromAdminDto(@Valid DentistDto dto) {
        return new Dentist(
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                passwordEncoder.encode(
                        securityUtil.generateAutomaticPassword()
                )
        );
    }

    public Dentist fromRegisteringDto(@Valid DentistRegisterDto dto) {
        return new Dentist(
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                passwordEncoder.encode(dto.password())
        );
    }

    public DentistDetailsDto toDetailedDto(Dentist dentist) {
        if (dentist == null) {
            throw new IllegalArgumentException("Dentist cannot be null");
        }

        DentistDto dentistDto = toDto(dentist);

        List<PatientForDentistDto> patientDtos = Optional.ofNullable(dentist.getPatientList())
                .orElse(Collections.emptySet())
                .stream()
                .map(dentistPatientMapper::toPatientOfDentistDto)
                .collect(Collectors.toList());


        return new DentistDetailsDto(
                dentistDto,
                patientDtos
        );
    }
}
