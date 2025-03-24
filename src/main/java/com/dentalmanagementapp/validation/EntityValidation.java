package com.dentalmanagementapp.validation;

public interface EntityValidation{
    void validateDentistExists(Long Id);
    void validateEmailUniqueness(String email);
    default void requireNonNull(Object o, String message) {
        if (o == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
