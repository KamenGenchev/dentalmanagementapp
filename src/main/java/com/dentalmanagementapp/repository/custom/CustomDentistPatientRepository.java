package com.dentalmanagementapp.repository.custom;

import com.dentalmanagementapp.entities.DentistPatient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomDentistPatientRepository {
    short findNextLocalIdForDentist();
    boolean patientExistsForDentist(String email);
    Page<DentistPatient> searchPatientsByName(String firstName, String lastName, Pageable pageable);
}
