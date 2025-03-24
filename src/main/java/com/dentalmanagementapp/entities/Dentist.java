package com.dentalmanagementapp.entities;

import com.dentalmanagementapp.entities.common.AbstractUser;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Dentist extends AbstractUser {
    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<DentistPatient> patientList = new HashSet<>();

    @OneToMany
    private final Set<OrthodonticRecord> orthodonticRecords = new HashSet<>();

    @OneToMany
    private final Set<PolyvalentRecord> polyvalentRecords = new HashSet<>();

    @Override
    public String getRole() {
        return "ROLE_DENTIST";
    }

    public Set<DentistPatient> getPatientList() {
        return patientList;
    }

    public void addPatient(DentistPatient patient) {
        patientList.add(patient);
    }

    protected Dentist() {
    }

    public Dentist(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }


    public void updateInformation(String firstName, String lastName, String email) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }
}