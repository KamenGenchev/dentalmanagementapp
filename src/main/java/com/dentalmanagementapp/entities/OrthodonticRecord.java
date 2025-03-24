package com.dentalmanagementapp.entities;

import com.dentalmanagementapp.entities.common.PatientRecord;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Filter;


@Entity
@Filter(name = "ownershipFilter")
@Filter(name = "patientFilter")
public class OrthodonticRecord extends PatientRecord {
    public OrthodonticRecord(DentistPatient dentistPatient, String description) {
        super(description, dentistPatient);
    }

    protected OrthodonticRecord() {
    }
}
