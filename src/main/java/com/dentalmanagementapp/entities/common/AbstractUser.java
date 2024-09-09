package com.dentalmanagementapp.entities.common;

import jakarta.persistence.*;
import org.hibernate.annotations.SoftDelete;

import java.util.Objects;

@MappedSuperclass
@SoftDelete
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected boolean deleted;

    @Column(nullable = false, length = 50)
    protected String firstName;

    @Column(nullable = false, length = 50)
    protected String lastName;

    @Column(unique = true, length = 200, nullable = false)
    protected String email;
    @Column(nullable = false, length = 30)
    protected String password;

    protected AbstractUser() {
    }

    protected AbstractUser(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUsername() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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