package com.dentalmanagementapp.repository.custom;

import com.dentalmanagementapp.entities.PolyvalentRecord;


import java.util.List;
import java.util.Optional;

public interface CustomPolyvalentRecordRepository {
    List<PolyvalentRecord> findAllWithFilter();
    Optional<PolyvalentRecord> findPolyvalentRecordWithAccess(Long id);
    boolean existsByIdWithOwnership(Long id);
}
