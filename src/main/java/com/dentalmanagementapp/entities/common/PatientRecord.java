package com.dentalmanagementapp.entities.common;

import com.dentalmanagementapp.entities.DentistPatient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@MappedSuperclass
public abstract class PatientRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private DentistPatient dentistPatient;
    private String description;
    private LocalDate recordDate;

    protected PatientRecord() {
    }

    protected PatientRecord(@NotNull String description, @NotNull LocalDate recordDate, @NotNull DentistPatient dentistPatient) {
        this.description = description;
        this.recordDate = recordDate;
        this.dentistPatient = dentistPatient;
    }

    public Long getId() {
        return id;
    }

    public DentistPatient getDentistPatient() {
        return dentistPatient;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

}
