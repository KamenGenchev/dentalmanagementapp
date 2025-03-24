package com.dentalmanagementapp.validation;

import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.repository.PolyvalentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolyvalentRecordValidation extends RecordValidation{
    private final PolyvalentRecordRepository polyvalentRecordRepository;

    @Autowired
    public PolyvalentRecordValidation(PolyvalentRecordRepository polyvalentRecordRepository) {
        this.polyvalentRecordRepository = polyvalentRecordRepository;
    }

    public void validateRecordAccess(Long id) {
        if (!polyvalentRecordRepository.existsByIdWithOwnership(id)){
            throw new NotFoundException("Polyvalent record with id " + id + " was not found or the user does not have access to it");
        }
    }
}
