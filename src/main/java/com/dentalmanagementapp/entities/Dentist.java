package com.dentalmanagementapp.entities;

import com.dentalmanagementapp.entities.common.AbstractUser;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
public class Dentist extends AbstractUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Patient> patientList;

    @Override
    public String getRole() {
        return "ROLE_DENTIST";
    }


    public Set<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(Set<Patient> patientList) {
        this.patientList = patientList;
    }
}