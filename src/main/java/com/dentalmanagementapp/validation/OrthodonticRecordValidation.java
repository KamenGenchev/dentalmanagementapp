package com.dentalmanagementapp.validation;

import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.repository.OrthodonticRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrthodonticRecordValidation extends RecordValidation {
    private final OrthodonticRecordRepository orthodonticRecordRepository;

    @Autowired
    public OrthodonticRecordValidation(OrthodonticRecordRepository orthodonticRecordRepository1) {
        this.orthodonticRecordRepository = orthodonticRecordRepository1;
    }

    public void validateRecordAccess(Long id) {
        if (!orthodonticRecordRepository.existsByIdWithOwnership(id)){
            throw new NotFoundException("Orthodontic record with id " + id + " was not found or the user does not have access to it");
        }
    }

}
