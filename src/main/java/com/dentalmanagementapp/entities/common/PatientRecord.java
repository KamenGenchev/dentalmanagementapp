package com.dentalmanagementapp.entities.common;

import com.dentalmanagementapp.entities.DentistPatient;
import jakarta.persistence.*;
import org.hibernate.annotations.*;

import java.time.LocalDate;

@MappedSuperclass
@SoftDelete

public abstract class PatientRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private boolean deleted;

    @Version
    private int version;

    @ManyToOne(fetch = FetchType.LAZY)
    private DentistPatient dentistPatient;

    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate recordDate;

    protected PatientRecord() {
    }

    protected PatientRecord(String description, DentistPatient dentistPatient) {
        this.description = description;
        this.dentistPatient = dentistPatient;
        this.recordDate = LocalDate.now();
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

    public void setDescription(String description) {
        this.description = description;
    }

}
