package com.dentalmanagementapp.repository;

import com.dentalmanagementapp.entities.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, Long>, IUserRepository {
    @Override
    Optional<Dentist> findByEmail(String email);
}
