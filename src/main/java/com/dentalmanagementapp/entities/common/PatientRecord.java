package com.dentalmanagementapp.entities.common;

import com.dentalmanagementapp.entities.Patient;
import jakarta.persistence.*;

import java.time.LocalDate;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PatientRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;
    private String description;
    private LocalDate recordDate;

}
