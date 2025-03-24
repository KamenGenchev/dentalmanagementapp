package com.dentalmanagementapp.entities;

import com.dentalmanagementapp.entities.common.DiagnoseLegend;
import com.dentalmanagementapp.entities.common.PatientRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Filter;

import java.time.LocalDate;

@Entity
@Filter(name = "ownershipFilter")
@Filter(name = "patientFilter")
public class PolyvalentRecord extends PatientRecord {

    @Enumerated(EnumType.STRING)
    @NotNull
    private DiagnoseLegend diagnoseLegend;


    protected PolyvalentRecord() {
    }

    public PolyvalentRecord(DiagnoseLegend diagnoseLegend, String description, DentistPatient patient) {
        super(description, patient);
        this.diagnoseLegend = diagnoseLegend;
    }

    public DiagnoseLegend getDiagnoseLegend() {
        return diagnoseLegend;
    }

    public void setDiagnoseLegend(DiagnoseLegend diagnoseLegend) {
        this.diagnoseLegend = diagnoseLegend;
    }

}