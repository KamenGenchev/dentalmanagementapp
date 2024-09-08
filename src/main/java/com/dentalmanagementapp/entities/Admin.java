package com.dentalmanagementapp.entities;

import com.dentalmanagementapp.entities.common.AbstractUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Value;

@Entity
public class Admin extends AbstractUser {
    @Transient
    private final String role = "ROLE_ADMIN";

    @Override
    public String getRole() {
        return role;
    }
}
