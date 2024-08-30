package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.DentistRegisterDto;
import com.dentalmanagementapp.entities.Dentist;

import java.util.List;
import java.util.Optional;

public interface DentistService {
    Optional<Dentist> findByEmail(String email);
    void registerDentist(DentistRegisterDto dto);
    Dentist createDentist(Dentist dentist);
    Optional<Dentist> getDentistById(Long id);
    List<Dentist> getAllDentists();
    Dentist updateDentist(Long id, Dentist dentist);
    void deleteDentist(Long id);
}
