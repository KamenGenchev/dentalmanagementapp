package com.dentalmanagementapp.validation;

import com.dentalmanagementapp.exception.custom.EntityAlreadyExistsException;
import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientValidation implements EntityValidation {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientValidation(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void validateDentistExists(Long Id) {
        if (!patientRepository.existsById(Id)) throw new NotFoundException("Patient with id " + Id + " not found");
    }

    @Override
    public void validateEmailUniqueness(String email) {
        if (patientRepository.existsByEmail(email)) throw new EntityAlreadyExistsException("Patient with this email already exists");
    }


}
