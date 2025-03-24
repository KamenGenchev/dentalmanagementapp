package com.dentalmanagementapp.validation;

public abstract class RecordValidation{

    public void requireNonNullId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Record ID cannot be null");
        }
    }

    public abstract void validateRecordAccess(Long id);

}
