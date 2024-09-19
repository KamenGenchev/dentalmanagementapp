package com.dentalmanagementapp.repository;

import com.dentalmanagementapp.entities.OrthodonticRecord;
import com.dentalmanagementapp.repository.custom.CustomOrthodonticRecordRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrthodonticRecordRepository extends JpaRepository<OrthodonticRecord, Long>, CustomOrthodonticRecordRepository {
}
