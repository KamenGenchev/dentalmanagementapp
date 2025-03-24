package com.dentalmanagementapp.entities;

import com.dentalmanagementapp.entities.common.AbstractUser;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patient extends AbstractUser {

    private String address;
    @Column(unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<DentistPatient> dentistList = new HashSet<>();

    protected Patient() {
    }

    private Patient(Builder builder) {
        super(builder.firstName, builder.lastName, builder.email, builder.password);
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
        this.dateOfBirth = builder.dateOfBirth;
    }

    public Set<DentistPatient> getDentistList() {
        return dentistList;
    }

    @Override
    public String getRole() {
        return "ROLE_PATIENT";
    }

    public int getAge() {
        return calculateAge(dateOfBirth);
    }

    public String getAddress() {
        return (address);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public void updateInformation(String email, String firstName, String lastName, String address, String phoneNumber, LocalDate localDate) {
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = localDate;
    }

    public void addDentist(DentistPatient dentistPatient) {
        dentistList.add(dentistPatient);
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String address = "N/A";
        private String phoneNumber = "N/A";
        private LocalDate dateOfBirth;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }

}
