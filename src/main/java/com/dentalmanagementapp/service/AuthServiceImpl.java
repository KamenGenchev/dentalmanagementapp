package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.DentistRegisterDto;
import com.dentalmanagementapp.entities.Dentist;
import com.dentalmanagementapp.repository.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private final DentistRepository dentistRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(DentistRepository userRepository, PasswordEncoder passwordEncoder) {
        this.dentistRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Dentist registerUser(DentistRegisterDto dto) {
        Dentist dentist = new Dentist();
        dentist.setFirstName(dto.firstName());
        dentist.setLastName(dto.lastName());
        dentist.setEmail(dto.email());
        dentist.setPassword(passwordEncoder.encode(dto.password()));
        dentistRepository.save(dentist);
        return dentist;
    }

    @Override
    public Dentist registerDentist(DentistRegisterDto dto) {
        return null;
    }
}
