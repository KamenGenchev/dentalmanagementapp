package com.dentalmanagementapp.repository;

import com.dentalmanagementapp.entities.common.AbstractUser;

import java.util.Optional;

public interface IUserRepository {
    Optional<? extends AbstractUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
