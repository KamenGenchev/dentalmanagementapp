package com.dentalmanagementapp.entities;

import com.dentalmanagementapp.entities.common.AbstractUser;
import jakarta.persistence.Entity;

@Entity
public class Admin extends AbstractUser {
    @Override
    public String getRole() {
        return "ROLE_ADMIN";
    }
}
