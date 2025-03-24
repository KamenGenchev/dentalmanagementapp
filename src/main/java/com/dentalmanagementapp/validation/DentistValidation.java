package com.dentalmanagementapp.validation;

import com.dentalmanagementapp.exception.custom.EntityAlreadyExistsException;
import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.repository.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DentistValidation implements EntityValidation{
    private final DentistRepository dentistRepository;

    @Autowired
    public DentistValidation(DentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    @Override
    public void validateDentistExists(Long Id) {
        if (!dentistRepository.existsById(Id)) throw new NotFoundException("Dentist with id " + Id + " not found");
    }

    @Override
    public void validateEmailUniqueness(String email) {
        if (dentistRepository.existsByEmail(email)) throw new EntityAlreadyExistsException("Dentist with this email already exists");
    }

}
