package com.dentalmanagementapp.security;

import com.dentalmanagementapp.repository.DentistRepository;
import com.dentalmanagementapp.repository.PatientRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final DentistRepository dentistRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public CustomUserDetailService(@NotNull DentistRepository dentistRepository, @NotNull PatientRepository patientRepository) {
        this.dentistRepository = dentistRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dentistRepository.findByEmail(username)
                .map(CustomUserDetails::new)
                .orElseGet(() -> patientRepository.findByUsername(username)
                        .map(CustomUserDetails::new)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        String.format("User with username: %s could not be found", username)
                                )
                        )
                );
    }
}
