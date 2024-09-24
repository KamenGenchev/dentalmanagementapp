package com.dentalmanagementapp.service.implementation;

import com.dentalmanagementapp.dtos.dentist.DentistDetailsDto;
import com.dentalmanagementapp.dtos.dentist.DentistDto;
import com.dentalmanagementapp.dtos.auth.DentistRegisterDto;
import com.dentalmanagementapp.entities.Dentist;
import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.mappers.DentistMapper;
import com.dentalmanagementapp.repository.DentistRepository;
import com.dentalmanagementapp.service.DentistService;
import com.dentalmanagementapp.validation.DentistValidation;
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
public class DentistServiceImpl implements DentistService {
    private final DentistRepository dentistRepository;
    private final DentistMapper dentistMapper;
    private final DentistValidation dentistValidation;

    @Autowired
    public DentistServiceImpl(DentistRepository dentistRepository, DentistMapper dentistMapper, DentistValidation dentistValidation) {
        this.dentistRepository = dentistRepository;
        this.dentistMapper = dentistMapper;
        this.dentistValidation = dentistValidation;
    }

    @Override
    @Transactional
    public void registerDentist(@Valid DentistRegisterDto dto) {
        dentistValidation.validateEmailUniqueness(dto.email());
        Dentist dentist = dentistMapper.fromRegisteringDto(dto);

        dentistRepository.save(dentist);
    }

    @Override
    @Transactional
    public Long createDentist(@Valid DentistDto dto) {
        dentistValidation.validateEmailUniqueness(dto.email());
        Dentist dentist = dentistMapper.fromAdminDto(dto);

        dentistRepository.save(dentist);
        return dentist.getId();
    }

    @Override
    public List<DentistDto> getAllDentists() {
        return dentistRepository.findAll().stream()
                .map(dentistMapper::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), List::copyOf));
    }

    @Override
    @Transactional(readOnly = true)
    public DentistDetailsDto getDentistById(Long id) {
        dentistValidation.requireNonNull(id, "Dentist ID cannot be null");

        return dentistRepository.findById(id)
                .map(dentistMapper::toDetailedDto)
                .orElseThrow(() -> new NotFoundException("Dentist with id: " + id + " was not found"));
    }

    @Override
    @Transactional
    public void updateDentist(Long id, @Valid DentistDto dto) {
        dentistValidation.requireNonNull(id, "Dentist ID cannot be null");

        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dentist with id: " + id + " was not found"));

        if (!Objects.equals(dentist.getEmail(), dto.email())) {
            dentistValidation.validateEmailUniqueness(dto.email());
        }

        dentist.updateInformation(dto.firstName(), dto.lastName(), dto.email());
        dentistRepository.save(dentist);
    }

    @Override
    @Transactional
    public void deleteDentist(Long id) {
        dentistValidation.requireNonNull(id, "Dentist ID cannot be null");
        dentistValidation.validateDentistExists(id);

        dentistRepository.deleteById(id);
    }
}
