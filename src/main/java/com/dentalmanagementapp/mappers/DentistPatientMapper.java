package com.dentalmanagementapp.mappers;

import com.dentalmanagementapp.dtos.dentistpatient.DentistForPatientDto;
import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import com.dentalmanagementapp.entities.Dentist;
import com.dentalmanagementapp.entities.DentistPatient;
import com.dentalmanagementapp.entities.Patient;
import org.springframework.stereotype.Component;

@Component
public class DentistPatientMapper {
    public PatientForDentistDto toPatientOfDentistDto(DentistPatient dentistPatient) {
        if (dentistPatient == null) {
            throw new IllegalArgumentException("DentistPatient cannot be null");
        }

        return new PatientForDentistDto(
                dentistPatient.getPatient().getFirstName(),
                dentistPatient.getPatient().getLastName(),
                dentistPatient.getLocalId()
        );
    }

    public  DentistPatient toEntity(PatientForDentistDto patientForDentistDto, Dentist dentist, Patient patient) {
        if (patientForDentistDto == null) {
            throw new IllegalArgumentException("DentistPatientDto cannot be null");
        }

        return new DentistPatient(
                dentist,
                patient,
                patientForDentistDto.localId()
        );
    }

    public DentistForPatientDto toDentistOfPatientDto(DentistPatient dentistPatient) {
        if (dentistPatient == null) {
            throw new IllegalArgumentException("DentistPatient cannot be null");
        }

        return new DentistForPatientDto(
                dentistPatient.getDentist().getEmail(),
                dentistPatient.getDentist().getFirstName(),
                dentistPatient.getDentist().getLastName()
        );
    }
    }