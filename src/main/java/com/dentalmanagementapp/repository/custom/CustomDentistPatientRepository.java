package com.dentalmanagementapp.repository.custom;

import com.dentalmanagementapp.entities.DentistPatient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomDentistPatientRepository {
    short findNextLocalIdForDentist();
    boolean patientExistsForDentist(String email);
    Page<DentistPatient> searchPatientsByName(String firstName, String lastName, Pageable pageable);

    Optional<DentistPatient> findPatientByLocalId(short localId);
}
