package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.dentist.DentistDetailsDto;
import com.dentalmanagementapp.dtos.dentist.DentistDto;
import com.dentalmanagementapp.dtos.auth.DentistRegisterDto;
import jakarta.validation.Valid;

import java.util.List;

public interface DentistService {
    void registerDentist(@Valid DentistRegisterDto dto);
    Long createDentist(@Valid DentistDto dentistDto);
    DentistDetailsDto getDentistById(Long id);
    List<DentistDto> getAllDentists();
    void updateDentist(Long id, @Valid DentistDto dentistDto);
    void deleteDentist(Long id);
}
