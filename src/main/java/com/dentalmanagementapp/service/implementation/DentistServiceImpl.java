package com.dentalmanagementapp.service.implementation;

import com.dentalmanagementapp.dtos.DentistRegisterDto;
import com.dentalmanagementapp.entities.Dentist;
import com.dentalmanagementapp.exception.custom.EntityAlreadyExistsException;
import com.dentalmanagementapp.repository.DentistRepository;
import com.dentalmanagementapp.service.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DentistServiceImpl implements DentistService {
    private final DentistRepository dentistRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DentistServiceImpl(DentistRepository dentistRepository, PasswordEncoder passwordEncoder) {
        this.dentistRepository = dentistRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerDentist(DentistRegisterDto dto) {
        if (dentistRepository.findByEmail(dto.email()).isPresent()) {
            throw new EntityAlreadyExistsException("Dentist with this email already exists");
        }

        Dentist dentist = new Dentist();
        dentist.setFirstName(dto.firstName());
        dentist.setLastName(dto.lastName());
        dentist.setEmail(dto.email());
        dentist.setPassword(passwordEncoder.encode(dto.password()));
        dentistRepository.save(dentist);
    }

    @Override
    public Dentist createDentist(Dentist dentist) {
        return null;
    }

    @Override
    public Optional<Dentist> getDentistById(Long id) {
        return dentistRepository.findById(id);
    }

    @Override
    public List<Dentist> getAllDentists() {
        return List.of();
    }

    @Override
    public Dentist updateDentist(Long id, Dentist dentist) {
        return null;
    }

    @Override
    public void deleteDentist(Long id) {

    }

    @Override
    public Optional<Dentist> findByEmail(String email) {
        return dentistRepository.findByEmail(email);
    }
}
