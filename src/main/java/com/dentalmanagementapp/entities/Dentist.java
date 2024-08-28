package com.dentalmanagementapp.entities;

import com.dentalmanagementapp.entities.common.AbstractUser;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import java.util.Set;

@Entity
public class Dentist extends AbstractUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @Value("${roles.dentist}")
    private String role;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Patient> patientList;
    @Override
    public String getRole() {
        return role;
    }

    public Set<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(Set<Patient> patientList) {
        this.patientList = patientList;
    }
}