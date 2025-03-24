package com.dentalmanagementapp.repository;

import com.dentalmanagementapp.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long>, IUserRepository {
    @Override
    Optional<Patient> findByEmail(String email);

}
