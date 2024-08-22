package com.dentalmanagementapp.entities.common;

import jakarta.persistence.*;
@MappedSuperclass
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String firstName;
    protected String lastName;
    @Column(unique = true)
    protected String email;

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
}
