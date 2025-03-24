package com.dentalmanagementapp.repository;

import com.dentalmanagementapp.entities.PolyvalentRecord;
import com.dentalmanagementapp.repository.custom.CustomPolyvalentRecordRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PolyvalentRecordRepository extends JpaRepository<PolyvalentRecord, Long>, CustomPolyvalentRecordRepository {

}
