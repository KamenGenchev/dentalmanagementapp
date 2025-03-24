package com.dentalmanagementapp.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SoftDelete;

import java.util.Objects;

@Entity
@Filter(name = "ownershipFilter")
public class DentistPatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotFound(action = NotFoundAction.EXCEPTION)
    private Dentist dentist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @NotFound(action = NotFoundAction.EXCEPTION)
    private Patient patient;

    @Column(nullable = false)
    private short localId;

    protected DentistPatient() {
    }

    public DentistPatient(Dentist dentist, Patient patient, short localId) {
        this.dentist = dentist;
        this.patient = patient;
        this.localId = localId;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public Patient getPatient() {
        return patient;
    }

    public short getLocalId() {
        return localId;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DentistPatient that)) return false;
        return localId == that.localId &&
                Objects.equals(id, that.id) &&
                Objects.equals(dentist, that.dentist) &&
                Objects.equals(patient, that.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dentist, patient, localId);
    }
}
