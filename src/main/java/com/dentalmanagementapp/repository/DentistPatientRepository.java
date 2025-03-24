package com.dentalmanagementapp.repository;

import com.dentalmanagementapp.entities.DentistPatient;
import com.dentalmanagementapp.repository.custom.CustomDentistPatientRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentistPatientRepository extends JpaRepository<DentistPatient, Long>, CustomDentistPatientRepository {
}
