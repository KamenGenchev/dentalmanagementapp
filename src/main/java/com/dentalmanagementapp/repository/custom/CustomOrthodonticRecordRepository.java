package com.dentalmanagementapp.repository.custom;

import com.dentalmanagementapp.entities.OrthodonticRecord;

import java.util.List;
import java.util.Optional;

public interface CustomOrthodonticRecordRepository {
    List<OrthodonticRecord> findAllWithFilter();
    Optional<OrthodonticRecord> findOrthodonticRecordWithAccess(Long id);
    boolean existsByIdWithOwnership(Long id);
}
