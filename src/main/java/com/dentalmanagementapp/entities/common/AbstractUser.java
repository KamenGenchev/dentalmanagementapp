package com.dentalmanagementapp.entities.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@MappedSuperclass
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false, length = 50)
    protected String firstName;

    @Column(nullable = false, length = 50)
    protected String lastName;

    @Column(unique = true, length = 200, nullable = false)
    protected String email;
    @Column(nullable = false, length = 25)
    protected String password;

    public String getUsername() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractUser that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}