package com.dentalmanagementapp.entities;

import com.dentalmanagementapp.entities.common.AbstractUser;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
public class Patient extends AbstractUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}) //manytomany
    private Dentist dentist;
    @Column(nullable = false)
    private short localId;

    private String firstName;
    private String middleName;
    private String lastName;

    private String address;
    private String telephone;
    private LocalDate dateOfBirth;
    private byte age;
//    @OneToMany
//    private List<PatientRecord> patientRecords;

    @Override
    public String getRole() {
        return "ROLE_PATIENT";
    }


}
